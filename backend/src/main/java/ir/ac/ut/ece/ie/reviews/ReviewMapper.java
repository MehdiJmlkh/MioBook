package ir.ac.ut.ece.ie.reviews;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "username", source = "review.user.username")
    ReviewDto toDto(Review review);
}
