package ir.ac.ut.ece.ie.auth;

public class GoogleLoginFailedException extends RuntimeException {
    public GoogleLoginFailedException(String message) {
        super(message);
    }
}
