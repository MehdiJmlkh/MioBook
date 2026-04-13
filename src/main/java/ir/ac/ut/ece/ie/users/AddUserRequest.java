package ir.ac.ut.ece.ie.users;

import lombok.Data;

@Data
public class AddUserRequest {
    private String role;
    private String username;
    private String password;
    private String email;
    private AddressDto address;
}
