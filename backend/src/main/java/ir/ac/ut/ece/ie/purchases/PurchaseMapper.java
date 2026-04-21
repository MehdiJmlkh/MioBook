package ir.ac.ut.ece.ie.purchases;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {

    @Mapping(target = "items",
            expression = "java(purchase.getItems().stream().map(PurchaseItemDto::fromPurchaseItem).toList())")
    @Mapping(target = "purchaseDate", source = "purchase.date")
    PurchaseDto toDto(Purchase purchase);

    default PurchasedBookDto toDto(PurchaseItem item) {
        var dto = new PurchasedBookDto();
        var book = item.getBook();
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor().getName());
        dto.setPublisher(book.getPublisher());
        dto.setGenres(book.getGenres());
        dto.setYear(book.getYear());
        dto.setPrice(item.getPrice());
        dto.setIsBorrowed(item.getIsBorrowed());
        return dto;
    }
}
