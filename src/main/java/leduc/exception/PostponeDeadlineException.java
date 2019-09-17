package leduc.exception;

public class PostponeDeadlineException extends DukeException {
    public PostponeDeadlineException(){
        super();
    }

    public String print(){
        return "\t PostponeDeadlineException:\n\t\t ☹ OOPS!!! The new deadline should not be before the old one";
    }
}
