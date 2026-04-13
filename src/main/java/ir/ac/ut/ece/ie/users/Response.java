package ir.ac.ut.ece.ie.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Response {
    private Boolean success;
    private String message;

    public static Response ok(String message) {
        return new Response(true, message);
    }

    public static Response failed(String message) {
        return new Response(false, message);
    }
}
