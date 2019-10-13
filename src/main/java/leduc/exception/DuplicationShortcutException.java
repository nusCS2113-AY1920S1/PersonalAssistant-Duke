package leduc.exception;

/**
 * Thrown when the shortcut name already exists
 */
public class DuplicationShortcutException extends DukeException {
    /**
     * Constructor of leduc.exception.DuplicationShortcutException
     *
     */
    public DuplicationShortcutException(){
        super();
    }

    /**
     * Tell the user that the shortcut name already exist
     * @return the error message
     */
    public String print(){
        return "\t DuplicationShortcutException:\n\t\t ☹ OOPS!!! The shortcut already exists";
    }

}
