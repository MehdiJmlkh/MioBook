package ir.ac.ut.ece.ie.books;

import ir.ac.ut.ece.ie.authors.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "genres", ignore = true)
    Book toBook(AddBookRequest request);

    @Mapping(target = "authorId", source = "book.author.id")
    @Mapping(target = "author", source = "book.author.name")
    @Mapping(target = "averageRating", expression = "java(book.getAverageRating())")
    @Mapping(target = "genres", expression = "java(book.getGenreNames())")
    BookDto toDto(Book book);

    @Mapping(target = "author", source = "book.author.name")
    BookContentDto toContentDto(Book book);
}
