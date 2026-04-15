package ir.ac.ut.ece.ie.books;

import ir.ac.ut.ece.ie.authors.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "author", ignore = true)
    Book toBook(AddBookRequest request);

    @Mapping(target = "author", source = "book.author.name")
    @Mapping(target = "averageRating", expression = "java(book.getAverageRating())")
    BookDto toDto(Book book);

    BookContentDto toContentDto(Book book);
}
