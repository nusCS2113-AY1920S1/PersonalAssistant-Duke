package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.text.ParseException;

/**
 * Abstract class to represent command.
 */
public abstract class Command {
    protected String userInputCommand;

    public abstract void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException, ParseException;
//    public abstract TaskList exe(Ui ui, Storage storage) throws DukeException, ParseException;

    public abstract boolean isExit();
}
