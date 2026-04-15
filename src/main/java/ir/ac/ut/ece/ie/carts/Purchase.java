package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.users.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class Purchase {
    private User user;
    private Set<PurchaseItem> items = new LinkedHashSet<>();
    private Integer totalCost;
    private LocalDateTime date;
}
