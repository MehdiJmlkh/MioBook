package ir.ac.ut.ece.ie.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import ir.ac.ut.ece.ie.users.Role;
import lombok.AllArgsConstructor;

import javax.crypto.SecretKey;
import java.util.Date;

@AllArgsConstructor
public class Jwt {
    private final Claims claims;
    private final SecretKey secretKey;

    public boolean isExpired() {
        return claims.getExpiration().before(new Date());
    }

    public Long getUserId() {
        return Long.valueOf(claims.getSubject());
    }

    public Role getRole() {
        return Role.valueOf(claims.get("role", String.class));
    }

    public String getUsername() {
        return claims.get("username", String.class);
    }

    public String toString() {
        return Jwts.builder()
                .claims(claims)
                .signWith(secretKey)
                .compact();
    }
}
