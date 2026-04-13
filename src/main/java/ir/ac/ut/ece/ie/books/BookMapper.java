package ir.ac.ut.ece.ie.books;

import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {
    Book toBook(AddBookRequest request);
}
