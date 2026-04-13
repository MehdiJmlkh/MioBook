package ir.ac.ut.ece.ie.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class User {
    private String role;
    private String username;
    private String password;
    private String email;
    private Address address;
}
