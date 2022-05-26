package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;

	// Method to adding common data to respose
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String userName = principal.getName();
		System.out.println("USERNAME" + userName);

		// get the user using username(Email)
		User user = this.userRepository.getUserByUserName(userName);
		System.out.println("User" + user);

		model.addAttribute("user", user);

	}

	// dashboard Home
	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {

		model.addAttribute("title", "User Dashboard");

		return "normal/user_dashbord";
	}

	// Open add form handler
	@GetMapping("/add-contact")
	public String openAddContactForm(Model model) {
		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());

		return "normal/add_contact_form";
	}

	// processing add contact form
	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute Contact contact, 
			@RequestParam("profileImage") MultipartFile file,
			Principal principal,HttpSession session) {

		try {

			String name = principal.getName();
			User user = this.userRepository.getUserByUserName(name);
			
			//processing and uploading file..
			
			if(file.isEmpty()) {
				//if the file is empty try our message
				System.out.println("File is empty");
				contact.setImage("contact.png");
			}
			else {
				// upload the file to folder and update the name to contact
				contact.setImage(file.getOriginalFilename());
				
				File saveFile = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
				Files.copy(file.getInputStream(), path ,StandardCopyOption.REPLACE_EXISTING);
				
				System.out.println("Image is uploaded");
			}

			user.getContacts().add(contact);
			contact.setUser(user);

			this.userRepository.save(user);
			System.out.println("DATA" + contact);
			System.out.println("Added to data base");
			
			// message success....
			session.setAttribute("message", new Message("Your contact is added !! Add more","success"));
			
			
			

		} catch (Exception e) {
			System.out.println("ERROR " + e.getMessage());
			e.printStackTrace();
			
			// message error
			session.setAttribute("message", new Message("Something went wrong !! Try again","danger"));
		}

		return "normal/add_contact_form";

	}
	
	// show contacts handler
	//per page =5[n]
	//current page = 0 [page]
	@GetMapping("/show-contacts/{page}")
	public String showContacts(@PathVariable("page")Integer page, Model m, Principal principal) {
		m.addAttribute("tilte","Show User Contacts");
//		// Contact list sharing by Current user Id
//		String userName = principal.getName();
//		User user = this.userRepository.getUserByUserName(userName);
//		List<Contact> contacts = user.getContacts();
		String userName = principal.getName();
		User user = this.userRepository.getUserByUserName(userName);
		//currentPage-page
		//Contact Per Page
		Pageable pageable = PageRequest.of(page, 3);
		
		
		Page<Contact> contacts = this.contactRepository.findContactByUser(user.getId(),pageable);
		m.addAttribute("contacts", contacts);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", contacts.getTotalPages());
		return "normal/show_contacts";
	}
	
	//showing particular conatact details
	@RequestMapping("/{cId}/contact")
	public String showContactDetail(@PathVariable("cId") Integer cId,Model model, Principal principal)
	{
		System.out.println(cId);
		
		Optional<Contact> optional = this.contactRepository.findById(cId);
		Contact contact = optional.get();
		
		//
		
		String userName = principal.getName();
		User user = this.userRepository.getUserByUserName(userName);
		
		if(user.getId()==contact.getUser().getId())
		   model.addAttribute("contact", contact);
		
		
		return "normal/contact_details";
	}

	//Delete contact handler
	
	@GetMapping("/delete/{cid}")
	public String deleteContact(@PathVariable("cid") Integer cId, Model model,Principal principal,HttpSession session  )
	{
		Optional<Contact> contactOptional = this.contactRepository.findById(cId);
		Contact contact = contactOptional.get();
		//check
		
		
		String userName = principal.getName();
		User user = this.userRepository.getUserByUserName(userName);
		
		if(user.getId()==contact.getUser().getId())
		{   contact.setUser(null);
			this.contactRepository.delete(contact);
		}
		
		session.setAttribute("message", new Message("Contact deleted successfully...", "success"));
		
		
		
		return "redirect:/user/show-contacts/0";
	}
	
	
	// Open update form handler
	
	@PostMapping("/update-contact/{cid}")
	public String updateForm(@PathVariable("cid") Integer cid,Model m)
	{
		
		m.addAttribute("title", "Update Contact");
		
		 Contact contact = this.contactRepository.findById(cid).get();
		 m.addAttribute("contact", contact);
		
		return "normal/update_form";
	}
	
// update contact handle
	@RequestMapping(value = "/process-update", method = RequestMethod.POST)
	public String updateHandler(@ModelAttribute Contact contact,@RequestParam("profileImage") MultipartFile file,Model m,HttpSession session,Principal principal) {
		//System.out.println("Contact Name"+contact.getName);
		
		
		
		
		try {
			Contact oldContactDetails = this.contactRepository.findById(contact.getcId()).get();
			
			if(!file.isEmpty())
			{
				// file work rewrite
              //delete old photo
				File deleteFile = new ClassPathResource("static/img").getFile();
				File file1 = new File(deleteFile, oldContactDetails.getImage());
				file1.delete();
				//update new photo
				File saveFile = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
				Files.copy(file.getInputStream(), path ,StandardCopyOption.REPLACE_EXISTING);
				
			    contact.setImage(file.getOriginalFilename());
								
			}
			else {
				contact.setImage(oldContactDetails.getImage());
			}
			
			User user = this.userRepository.getUserByUserName(principal.getName());
			
			contact.setUser(user);
			this.contactRepository.save(contact);
			
			session.setAttribute("message", new Message("Your contact is updated...","success"));
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		
		return"redirect:/user/"+contact.getcId()+"/contact";
	}
	
	// your profile handler
	@GetMapping("/profile")
	public String yourProfile(Model model)
	{
		model.addAttribute("title","Profile Page");
		return "normal/profile";
		
	}
}
