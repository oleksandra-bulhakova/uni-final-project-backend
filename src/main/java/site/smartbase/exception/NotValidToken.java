package site.smartbase.exception;

public class NotValidToken extends RuntimeException {
    public NotValidToken(String message) {
        super(message);
    }
}
