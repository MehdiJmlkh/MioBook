package ir.ac.ut.ece.ie.users;

import ir.ac.ut.ece.ie.auth.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        builder = @org.mapstruct.Builder(disableBuilder = true))
public interface UserMapper {
    Admin toAdmin(AddUserRequest request);
    Customer toCustomer(AddUserRequest request);

    @Mapping(target = "role", expression = "java(user.getRole().toString())")
    UserDto toDto(User user);
}
