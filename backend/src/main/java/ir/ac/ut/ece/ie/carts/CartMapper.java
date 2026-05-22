package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.purchases.PurchaseItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "id", ignore = true)
    PurchaseItem toPurchaseItem(CartItem cartItem);
}
