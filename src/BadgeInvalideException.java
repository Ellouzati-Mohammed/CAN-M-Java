/**
 * @author Ellou
 **/
public class BadgeInvalideException extends RuntimeException {
    private String message;

    public BadgeInvalideException(String message) {
        super(message);
        this.message = message;
    }
}
