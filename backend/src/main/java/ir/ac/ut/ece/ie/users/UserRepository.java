package ir.ac.ut.ece.ie.users;

import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserRepository {
    private final Set<User> users = new LinkedHashSet<>();
    private Long lastId = 0L;

    public void addUser(User user) {
        user.setId(lastId++);
        users.add(user);
    }

    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    public Optional<User> findById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public Optional<User> findByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
}
