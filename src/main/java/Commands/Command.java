package Commands;

import Interface.LookupTable;
import Interface.Storage;
import Interface.Ui;
import Tasks.TaskList;

/**
 * Abstract class Command with methods representing all the Command subclasses to be
 * carried out when an input is entered by the user.
 */
public abstract class Command {
    public abstract String execute(LookupTable LT, TaskList events, TaskList deadlines, Ui ui, Storage storage) throws Exception;
}