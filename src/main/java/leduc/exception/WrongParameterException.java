package leduc.exception;

public class WrongParameterException extends DukeException {
    /**
     * Constructor of leduc.exception.PostponeDeadlineException
     *
     */
    public WrongParameterException(){
        super();
    }

    /**
     * Tell the user that the new deadline should not be before the old one.
     * @return the error message
     */
    public String print(){
        return "\t WrongParameterException:\n\t\t ☹ OOPS!!! The parameters are wrong";
    }
}
