package ir.ac.ut.ece.ie.users;

import lombok.*;

@Getter
@Setter
public class Customer extends User {
    private Integer balance;

    public void addCredit(Integer amount) {
        balance += amount;
    }

    public void withdrawCredit(Integer amount) {
        balance -= amount;
    }
}
