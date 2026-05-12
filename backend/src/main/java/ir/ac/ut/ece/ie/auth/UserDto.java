package ir.ac.ut.ece.ie.auth;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String email;
    private String role;
}
