package ir.ac.ut.ece.ie.authors;

import org.mapstruct.Mapper;

@Mapper
public interface AuthorMapper {
    Author toAuthor(AddAuthorRequest request);
}
