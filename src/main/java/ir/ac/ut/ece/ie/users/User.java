package ir.ac.ut.ece.ie.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
    private Integer balance;

    public void addCredit(Integer amount) {
        balance += amount;
    }

    public void withdrawCredit(Integer amount) {
        balance -= amount;
    }
}
