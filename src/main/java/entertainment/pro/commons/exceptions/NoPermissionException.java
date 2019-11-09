package entertainment.pro.commons.exceptions;

/**
 * Exception for when user do not have permission to that command.
 */
public class NoPermissionException extends Exceptions {
    public NoPermissionException(String message) {
        super(message);
    }
}
