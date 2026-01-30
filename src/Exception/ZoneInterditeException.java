package Exception;
/**
 * @author Ellou
 **/
public class ZoneInterditeException extends RuntimeException {
    private String message;

    public ZoneInterditeException(String message) {
        super(message);
        this.message = message;
    }
}
