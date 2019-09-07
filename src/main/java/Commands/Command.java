package Commands;

import ControlPanel.*;
import Tasks.*;

import java.text.ParseException;


public abstract class Command {
    public Command(){

    }

    public abstract boolean isExit();
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, ParseException;
}
