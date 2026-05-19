package ir.ac.ut.ece.ie.auth;

import ir.ac.ut.ece.ie.users.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuthRepository {
    private User user = null;

    public void setAuthenticatedUser(User user) {
        this.user = user;
    }

    public void removeAuthenticatedUser() {
        user = null;
    }

    public Optional<User> getAuthenticatedUser() {
        if (user == null) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    public boolean isLoggedIn(User user) {
        if (this.user == null) {
            return false;
        }
        return this.user.equals(user);
    }
}
