package ir.ac.ut.ece.ie.users;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private Long id;
    private Role role;
    private String username;
    private String password;
    private String email;
    private Address address;
    private Integer balance;

    public void addCredit(Integer amount) {
        balance += amount;
    }

    public void withdrawCredit(Integer amount) {
        balance -= amount;
    }
}
