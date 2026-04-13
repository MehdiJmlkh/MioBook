package ir.ac.ut.ece.ie.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AddUserRequest {
    private String role;
    private String username;
    private String password;
    private String email;
    private AddressDto address;
}
