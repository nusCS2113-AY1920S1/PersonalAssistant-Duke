package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

public abstract class CommandTest {
    protected String userInputCommand;

    public abstract ArrayList<String> feedback(TaskList taskList, Ui ui, Storage storage) throws DukeException;

    public abstract boolean isExit();
}
