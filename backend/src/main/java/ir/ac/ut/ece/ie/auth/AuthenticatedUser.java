package ir.ac.ut.ece.ie.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticatedUser {
    private final Long id;
    private final String username;
}
