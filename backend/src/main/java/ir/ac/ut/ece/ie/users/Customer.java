package ir.ac.ut.ece.ie.users;

import ir.ac.ut.ece.ie.carts.Cart;
import ir.ac.ut.ece.ie.purchases.Purchase;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "id")
public class Customer extends User {
    @OneToOne(mappedBy = "customer", cascade = CascadeType.PERSIST)
    private Wallet wallet;

    @OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST)
    private Cart cart;

    @OneToMany(mappedBy = "user")
    private Set<Purchase> purchases;

    public void addEmptyWallet() {
        wallet = new Wallet();
        wallet.setBalance(0);
        wallet.setCustomer(this);
    }

    public void addEmptyCart() {
        cart = new Cart();
        cart.setUser(this);
    }

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
