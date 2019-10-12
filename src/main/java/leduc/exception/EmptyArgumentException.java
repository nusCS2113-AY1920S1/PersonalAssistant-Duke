package leduc.exception;

/**
 * Exception when there is no argument
 */
public class EmptyArgumentException extends DukeException {

    /**
     * Constructor of leduc.exception.EmptyArgumentException
     *
     */
    public EmptyArgumentException(){
        super();
    }

    /**
     * Tell the user that the tasks chosen should be a deadline task.
     * @return the error message
     */
    public String print(){
        return "\t EmptyArgumentException:\n\t\t ☹ OOPS!!! There should have an argument";
    }
}
