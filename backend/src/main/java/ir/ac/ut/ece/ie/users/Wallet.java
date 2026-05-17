package ir.ac.ut.ece.ie.users;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Wallet {
    private Integer balance;

    public void addCredit(Integer amount) {
        balance += amount;
    }

    public void withdrawCredit(Integer amount) {
        balance -= amount;
    }
}
