package ir.ac.ut.ece.ie;

import ir.ac.ut.ece.ie.users.AddUserRequest;
import ir.ac.ut.ece.ie.users.AddressDto;
import ir.ac.ut.ece.ie.users.UserRepository;
import ir.ac.ut.ece.ie.users.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);


//        var request = new AddUserRequest();
//        request.setRole("customer");
//        request.setUsername("Mosh");
//        request.setPassword("123");
//        request.setEmail("mosh@codewithmosh.com");
//        request.setAddress(new AddressDto("country", "city"));
//        var userService = context.getBean(UserService.class);
//        var response = userService.addUser(request);
//
//        var userRepository = context.getBean(UserRepository.class);
//        var user = userRepository.findByUsername("Mosh");
//        System.out.println(user);
//        System.out.println(response);
    }
}