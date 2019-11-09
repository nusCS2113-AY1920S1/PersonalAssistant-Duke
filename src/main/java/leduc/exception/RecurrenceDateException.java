package leduc.exception;

/**
 * Exception when there is a date conflict between two recurrent task
 */
public class RecurrenceDateException extends DukeException {
    /**
     * Contructor of RecurrenceDateException
     */
    public RecurrenceDateException() {
        super();
    }

    /**
     * Tell the user that there will be a conflict date with event if the recurrence is created
     * @return the error message
     */
    @Override
    public String print() {
        return "\t RecurrenceException:\n\t\t ☹ OOPS!!! You are trying to make the event recurrent but there will be a conflict date if those events are created" +
                "\n\t\t\t The event has not been created, please check the date";
    }

}
