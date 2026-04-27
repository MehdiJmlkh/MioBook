package ir.ac.ut.ece.ie.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AddUserRequest {
    @Pattern(regexp = "customer|admin", message = "Role must be customer or admin.")
    private String role;

    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "Only English letters, numbers, -, and _ are allowed.")
    private String username;

    @Size(min = 4, message = "Password must be at least 4 characters long")
    private String password;

    @Email(message = "Invalid email format")
    private String email;

    private AddressDto address;
}
