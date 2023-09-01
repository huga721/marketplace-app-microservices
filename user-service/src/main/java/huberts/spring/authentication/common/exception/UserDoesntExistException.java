package huberts.spring.authentication.common.exception;

public class UserDoesntExistException extends RuntimeException {
    public UserDoesntExistException(String message) {
        super(message);
    }
}