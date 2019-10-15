package leduc.exception;


/**
 * Represent a exception when the format of a prioritize command is not respected.
 */
public class PrioritizeFormatException extends DukeException {
    /**
     * Constructor of leduc.exception.PrioritizeFormatException.
     */
    public PrioritizeFormatException(){
        super();
    }

    /**
     * Provide the prioritize format to respect to the user.
     *
     */
    public String print(){
        return "\t PrioritizeFormatException:\n\t\t ☹ OOPS!!! Please respect the prioritize command format" +
                "\n\t\t\t prioritize INDEX prio INDEX";
    }
}
