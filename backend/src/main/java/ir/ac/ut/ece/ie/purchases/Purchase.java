package ir.ac.ut.ece.ie.purchases;

import ir.ac.ut.ece.ie.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "total_cost")
    private Integer totalCost;

    @Column(name = "date")
    private LocalDateTime date;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User user;

    private Set<PurchaseItem> items = new LinkedHashSet<>();
}
