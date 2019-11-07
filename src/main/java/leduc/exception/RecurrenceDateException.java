package leduc.exception;

public class RecurrenceDateException extends DukeException {
    public RecurrenceDateException() {
        super();
    }
    @Override
    public String print() {
        return "\t RecurrenceException:\n\t\t ☹ OOPS!!! You are trying to make the event recurrent but there will be a conflict date if those events are created" +
                "\n\t\t\t The event has not been created, please check the date";
    }

}
