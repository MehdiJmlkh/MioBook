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

    private static final Duration SESSION_DURATION = Duration.ofMinutes(20);

    public String createSession(Long userId) {
        String token = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(
                createSessionKey(token),
                userId,
                SESSION_DURATION
        );

        return token;
    }

    public Long getSession(String token) {
        Number id = redisTemplate
                .opsForValue()
                .get(createSessionKey(token));

        if (id == null) {
            return null;
        }
        refreshSession(token);
        return id.longValue();
    }

    public void deleteSession(String token) {
        redisTemplate.delete(createSessionKey(token));
    }

    private void refreshSession(String token) {
        redisTemplate.expire(createSessionKey(token), SESSION_DURATION);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void clearSessions() {
        Set<String> keys = redisTemplate.keys("session:*");

        if (!keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    private String createSessionKey(String token) {
        return "session:" + token;
    }
}
