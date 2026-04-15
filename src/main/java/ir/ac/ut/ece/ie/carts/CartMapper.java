package ir.ac.ut.ece.ie.carts;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    PurchaseItem toPurchaseItem(CartItem cartItem);
}
