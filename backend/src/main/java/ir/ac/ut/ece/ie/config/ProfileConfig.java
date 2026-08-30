package ir.ac.ut.ece.ie.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProfileConfig {
    @Value("${spring.profiles.active:dev}")
    private String profile;

    public boolean isDev() {
        return profile.equals("dev");
    }
}
