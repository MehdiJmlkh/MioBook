package ir.ac.ut.ece.ie.auth;

import ir.ac.ut.ece.ie.users.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    UserDto toDto(User user);
}
