package ir.ac.ut.ece.ie.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "balance")
    private Integer balance;

    @OneToOne
    @JoinColumn(name = "customer_id")
    @MapsId
    private Customer customer;

    public void addCredit(Integer amount) {
        balance += amount;
    }

    public void withdrawCredit(Integer amount) {
        balance -= amount;
    }
}
