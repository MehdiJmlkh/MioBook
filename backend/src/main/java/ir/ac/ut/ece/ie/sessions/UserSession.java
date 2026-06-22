package ir.ac.ut.ece.ie.sessions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class UserSession {
    private Long userId;
    private Instant createdAt;
}
