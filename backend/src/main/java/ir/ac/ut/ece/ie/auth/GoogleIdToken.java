package ir.ac.ut.ece.ie.auth;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GoogleIdToken {
    private String email;
    private String name;
}
