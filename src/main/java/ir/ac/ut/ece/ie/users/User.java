package ir.ac.ut.ece.ie.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class User {
    private Role role;
    private String username;
    private String password;
    private String email;
    private Address address;
    private Integer wallet;
}
