package ir.ac.ut.ece.ie.users;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "role", expression = "java(Role.valueOf(request.getRole().toUpperCase()))")
    User toUser(AddUserRequest request);
}
