package leduc.exception;

public class RecurrenceException extends DukeException {
    public RecurrenceException() {
        super();
    }

    @Override
    public String print() {
        return "\t RecurrenceException:\n\t\t ☹ OOPS!!! Please respect the recurrence format" +
                "\n\t\t\t recu TYPEOFRECURRENCE NBRECURRENCE";
    }
}
