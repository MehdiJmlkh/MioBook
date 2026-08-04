package ir.ac.ut.ece.ie.users;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Query("SELECT u.username FROM User u WHERE u.username LIKE CONCAT(:prefix, '%')")
    List<String> findUsernamesStartingWith(@Param("prefix") String prefix);
}
