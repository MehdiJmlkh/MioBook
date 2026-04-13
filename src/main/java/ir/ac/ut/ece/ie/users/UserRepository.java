package ir.ac.ut.ece.ie.users;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class UserRepository {
    private final Set<User> users = new LinkedHashSet<>();

    public void addUser(User user) {
        users.add(user);
    }

    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    public Optional<User> findByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
}
