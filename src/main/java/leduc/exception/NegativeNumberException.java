package leduc.exception;

public class NegativeNumberException extends DukeException {
    public NegativeNumberException() {
        super();
    }

    @Override
    public String print() {
        return "\t NegativeNumberException:\n\t\t ☹ OOPS!!! There can't be a negative number here.";
    }
}
