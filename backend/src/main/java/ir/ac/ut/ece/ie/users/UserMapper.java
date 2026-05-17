package ir.ac.ut.ece.ie.users;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Admin toAdmin(AddUserRequest request);
    Customer toCustomer(AddUserRequest request);
}
