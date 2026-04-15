package ir.ac.ut.ece.ie.purchases;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {

    @Mapping(target = "items",
            expression = "java(purchase.getItems().stream().map(PurchaseItemDto::fromPurchaseItem).toList())")
    @Mapping(target = "purchaseDate", source = "purchase.date")
    PurchaseDto toDto(Purchase purchase);
}
