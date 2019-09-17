package leduc.exception;


import leduc.Ui;

public class SnoozeTypeException extends DukeException {
    public SnoozeTypeException(Ui ui){
        super(ui);
    }

    public void print(){
        super.ui.display("\t SnoozeTypeException:\n\t\t ☹ OOPS!!! The snoozed task should be a " +
                "deadline task");
    }


}
