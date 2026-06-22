package ir.ac.ut.ece.ie.sessions;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final RedisTemplate<String, Object> redisTemplate;

    private static final Duration SESSION_DURATION = Duration.ofHours(24);

    public String createSession(Long userId) {
        String token = UUID.randomUUID().toString();

        UserSession session = new UserSession(
                userId,
                Instant.now());

        redisTemplate.opsForValue().set(
                "session:" + token,
                session,
                SESSION_DURATION
        );

        return token;
    }

    public UserSession getSession(String token) {
        return (UserSession) redisTemplate
                .opsForValue()
                .get("session:" + token);
    }

    public void deleteSession(String token) {
        redisTemplate.delete("session:" + token);
    }
}
