package ir.ac.ut.ece.ie.users;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private String username;
    private String password;
    private String email;
    private Address address;
}
