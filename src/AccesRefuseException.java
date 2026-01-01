/**
 * @author Ellou
 **/
// AccesRefuseException.java
public class AccesRefuseException extends RuntimeException {
    private String message;

    public AccesRefuseException(String message) {
        super(message);
        this.message = message;
    }
}
