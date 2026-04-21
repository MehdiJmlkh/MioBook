package ir.ac.ut.ece.ie.authors;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toAuthor(AddAuthorRequest request);
}
