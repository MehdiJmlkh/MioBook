package ir.ac.ut.ece.ie.users;

import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class AdminRepository {
    private final Set<Admin> admins = new LinkedHashSet<>();

    public void addAdmin(Admin admin) {
        admins.add(admin);
    }

    public Optional<Admin> findByUsername(String username) {
        return admins.stream()
                .filter(admin -> admin.getUsername().equals(username))
                .findFirst();
    }
}
