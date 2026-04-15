package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.purchases.PurchaseItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    PurchaseItem toPurchaseItem(CartItem cartItem);
}
