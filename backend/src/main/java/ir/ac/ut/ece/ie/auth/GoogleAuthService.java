package ir.ac.ut.ece.ie.auth;

import ir.ac.ut.ece.ie.config.FrontendConfig;
import ir.ac.ut.ece.ie.config.GoogleAuthConfig;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@AllArgsConstructor
@Service
public class GoogleAuthService {
    private final GoogleAuthConfig googleAuthConfig;
    private final FrontendConfig frontendConfig;
    private final RestClient restClient;


    public GoogleTokenResponse getToken(String code) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        body.add("client_id", googleAuthConfig.getClientId());
        body.add("client_secret", googleAuthConfig.getClientSecret());
        body.add("code", code);
        body.add("grant_type", "authorization_code");
        body.add("redirect_uri", frontendConfig.getAuthCallbackUrl());

        return restClient.post()
                .uri("https://oauth2.googleapis.com/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .body(GoogleTokenResponse.class);
    }
}
