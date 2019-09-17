package leduc.exception;


import leduc.Ui;


/**
 * Represent a exception when the task to snooze is not a deadline task
 */
public class SnoozeTypeException extends DukeException {
    /**
     * Constructor of leduc.exception.SnoozeTypeException
     * @param ui ui leduc.Ui which deals with the interactions with the user.
     */
    public SnoozeTypeException(Ui ui){
        super(ui);
    }

    /**
     * Tell the user that the task to snooze is not a deadline task.
     */
    public void print(){
        super.ui.display("\t SnoozeTypeException:\n\t\t ☹ OOPS!!! The snoozed task should be a " +
                "deadline task");
    }


}
