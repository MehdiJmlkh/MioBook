package ir.ac.ut.ece.ie.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.frontend")
@Data
public class FrontendConfig {
    private String url;
    private String authCallback;

    public String getAuthCallbackUrl() {
        return url + authCallback;
    }
}
