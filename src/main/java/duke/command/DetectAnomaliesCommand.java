package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.text.ParseException;

public class DetectAnomaliesCommand extends Command {

    public DetectAnomaliesCommand(String userInputCommand) {
        this.userInputCommand = userInputCommand;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException, ParseException {

    }

    @Override
    public boolean isExit() {
        return false;
    }
}
