package ir.ac.ut.ece.ie.users;

import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User toUser(AddUserRequest request);
}
