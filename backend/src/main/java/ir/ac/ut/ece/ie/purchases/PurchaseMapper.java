package ir.ac.ut.ece.ie.purchases;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {

    @Mapping(target = "items",
            expression = "java(purchase.getItems().stream().map(PurchaseItemDto::fromPurchaseItem).toList())")
    @Mapping(target = "purchaseDate", source = "purchase.date", dateFormat = "yyyy-MM-dd HH:mm")
    PurchaseDto toDto(Purchase purchase);

    default PurchasedBookDto toDto(PurchaseItem item) {
        var dto = new PurchasedBookDto();
        var book = item.getBook();
        dto.setId(book.getId());
        dto.setAuthorId(book.getAuthor().getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor().getName());
        dto.setPublisher(book.getPublisher());
        dto.setGenres(book.getGenres());
        dto.setYear(book.getYear());
        dto.setPrice(item.getPrice());
        dto.setIsBorrowed(item.getIsBorrowed());
        if (item.getIsBorrowed()) {
            dto.setExpiredDate(
                item.getDate()
                        .plusDays(item.getBorrowDays())
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            );
        }
        return dto;
    }
}
