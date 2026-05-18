package ir.ac.ut.ece.ie.users;

import ir.ac.ut.ece.ie.carts.Cart;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "id")
public class Customer extends User {
    @OneToOne(mappedBy = "customer")
    private Wallet wallet = new Wallet();

    @OneToOne(mappedBy = "user")
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
