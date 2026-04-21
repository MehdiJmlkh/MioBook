package ir.ac.ut.ece.ie.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
