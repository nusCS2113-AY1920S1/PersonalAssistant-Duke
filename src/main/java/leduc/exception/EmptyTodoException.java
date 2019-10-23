package leduc.exception;

/**
 * Represent a exception when the description of the todo task is not given by the user.
 */
public class EmptyTodoException extends DukeException {
    /**
     * Constructor of leduc.exception.EmptyTodoException.
     */
    public EmptyTodoException(){
        super();
    }

    /**
     * Ask for a description for the todo task to the user.
     * @return the error message
     */
    public String print(){
        return "\t emptyTodoException:\n\t\t ☹ OOPS!!! The description of a todo cannot be empty.";
    }
}
