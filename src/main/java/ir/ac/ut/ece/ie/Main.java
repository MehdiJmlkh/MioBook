package ir.ac.ut.ece.ie;

import ir.ac.ut.ece.ie.users.AddUserRequest;
import ir.ac.ut.ece.ie.users.AddressDto;
import ir.ac.ut.ece.ie.users.UserService;

public class Main {
    public static void main(String[] args) {
        var request = new AddUserRequest();
        request.setRole("customer");
        request.setUsername("Mosh");
        request.setPassword("1234");
        request.setEmail("mosh@codewithmosh.com");
        request.setAddress(new AddressDto("country", "city"));
        var userService = new UserService();
        var response = userService.addUser(request);

        System.out.println(response);
    }
}