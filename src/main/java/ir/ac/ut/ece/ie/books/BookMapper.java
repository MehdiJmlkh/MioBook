package ir.ac.ut.ece.ie.books;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toBook(AddBookRequest request);
}
