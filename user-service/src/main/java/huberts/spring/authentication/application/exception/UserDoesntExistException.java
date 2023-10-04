package huberts.spring.authentication.application.exception;

public class UserDoesntExistException extends RuntimeException {
    public UserDoesntExistException(String message) {
        super(message);
    }
}