/**
 * @author Ellou
 **/
public class DonneeInvalideException extends RuntimeException {
    private String message;

    public DonneeInvalideException(String message) {
        super(message);
        this.message = message;
    }
}
