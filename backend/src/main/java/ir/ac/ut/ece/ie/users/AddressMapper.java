package ir.ac.ut.ece.ie.users;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toEntity(AddressDto dto);
}
