package ir.ac.ut.ece.ie.users;

import ir.ac.ut.ece.ie.carts.Cart;
import lombok.*;

@Getter
@Setter
public class Customer extends User {
    private Wallet wallet = new Wallet();
    private Cart cart;

    public void addCredit(Integer amount) {
        wallet.addCredit(amount);
    }

    public void withdrawCredit(Integer amount) {
        wallet.withdrawCredit(amount);
    }

    public Integer getBalance() {
        return wallet.getBalance();
    }

    public void setBalance(Integer balance) {
        wallet.setBalance(balance);
    }
}
