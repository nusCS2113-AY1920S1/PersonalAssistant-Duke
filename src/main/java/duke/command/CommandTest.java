package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.list.tasklist.TaskList;
import duke.ui.Ui;

import java.util.ArrayList;

public abstract class CommandTest {

    protected String userInput;
    public CommandType commandType;

    public enum CommandType {
        BOOKING, RECIPE, INGREDIENT
    }

    public abstract ArrayList<String> execute(TaskList taskList, Ui ui, Storage storage) throws DukeException;

    public abstract boolean isExit();
}
