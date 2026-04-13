package ir.ac.ut.ece.ie.users;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AddressDto {
    private String country;
    private String city;
}
