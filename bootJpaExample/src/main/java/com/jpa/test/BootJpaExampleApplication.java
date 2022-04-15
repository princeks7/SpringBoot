package com.jpa.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.jpa.test.doa.UserRepository;
import com.jpa.test.entities.User;

@SpringBootApplication
public class BootJpaExampleApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BootJpaExampleApplication.class, args);
		
		UserRepository userRepository = context.getBean(UserRepository.class);
//		
//		User user = new User();
//		user.setName("Prince Kumar Singh");
//		user.setCity("Kolkata");
//		user.setStatus("I am java programmer");
//		
//		User user1 = userRepository.save(user);
//		System.out.println(user1);
		
		// Create object of User
		User user1 = new User();
		user1.setName("Aman");
		user1.setCity("Kolkata");
		user1.setStatus("I am DBA programmer");
		
		User user2 = new User();
		user2.setName("Shashi");
		user2.setCity("Asssam");
		user2.setStatus("I am API Java programmer");
		
		
//		// saving single user
//		User resultUser = userRepository.save(user1);
//	
//		System.out.println("saved user" +resultUser);
//        System.out.println("done");	
//		
//	
		
		
//		// saving multiple objects
//		// List<User> users = List.of(user1,user2);
//		ArrayList<User> users = new ArrayList<User>();
//		users.add(user1);
//		users.add(user2);
//	     Iterable<User> result=userRepository.saveAll(users);	
//	
//	     result.forEach(user->{System.out.println(user);});
//	     
//	  //   System.out.println("saved user "+result);
//	     System.out.println("done");
		
		// update the user of id 11
//		Optional<User> optional = userRepository.findById(2);
//		
//		User user = optional.get();
//		
//		user.setName("Ankit Tiwari");
//		
//		userRepository.save(user);
//		
//		System.out.println(user);
		
		//how to get data
		//findById(id)- return Optional Containing your data
		//
		
		//Iterable<User> ite = userRepository.findAll();
		

//	Old methods
	//	ite.forEach(new Consumer<User>() {
//
//			@Override
//			public void accept(User t) {
//
//				System.out.println(t);
//			}
			
//		ite.forEach(a->{System.out.println(a);} );  // lamda function
		
		
		// Deleting the user elmemnt
//		userRepository.deleteById(2);
//		System.out.println("deleted");
		
		
		
		//deleting many user
//		
//		Iterable<User> allusers = userRepository.findAll();
//		
//		allusers.forEach(a -> System.out.println(a));
//		userRepository.deleteAll(allusers);
		
		
		//List<User> findByName = userRepository.findByName("Aman");
//		List<User> findByNameOrCity = userRepository.findByNameOrCity("Aman","Asssam");
//		findByNameOrCity.forEach(a->{System.out.println(a);System.out.println("lamda");} );  // lamda function
		
		
		List<User> allUser = userRepository.getAllUser();
		allUser.forEach(e->System.out.println(e));

		List<User> userByName = userRepository.getUserByName("Aman");
		userByName.forEach(e->System.out.println(e));

	}
}
