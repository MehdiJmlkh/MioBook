package ir.ac.ut.ece.ie.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.oauth.google")
@Data
public class GoogleAuthConfig {
    private String clientId;
    private String clientSecret;
}
