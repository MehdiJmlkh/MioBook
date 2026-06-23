package ir.ac.ut.ece.ie.sessions;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final RedisTemplate<String, Long> redisTemplate;

    private static final Duration SESSION_DURATION = Duration.ofHours(24);

    public String createSession(Long userId) {
        String token = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(
                "session:" + token,
                userId,
                SESSION_DURATION
        );

        return token;
    }

    public Long getSession(String token) {
        Number id = redisTemplate
                .opsForValue()
                .get("session:" + token);

        if (id == null) {
            return null;
        }
        return id.longValue();
    }

    public void deleteSession(String token) {
        redisTemplate.delete("session:" + token);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void clearSessions() {
        Set<String> keys = redisTemplate.keys("session:*");

        if (!keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }
}
