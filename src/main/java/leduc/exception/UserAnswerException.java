package leduc.exception;


/**
 * Represent a exception when the user did not answer correctly the question.
 */
public class UserAnswerException extends DukeException {
    /**
     * Constructor of leduc.exception.UserAnswerException.
     */
    public UserAnswerException(){
        super();
    }

    /**
     * Tell the user to answer correctly the question.
     *
     */
    public String print(){
        return "\t UserAnswerException:\n\t\t ☹ OOPS!!! Please answer correctly the question.";
    }
}
