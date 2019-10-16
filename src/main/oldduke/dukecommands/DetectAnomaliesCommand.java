package duke.command.dukecommands;

import duke.command.Command;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.list.tasklist.TaskList;
import duke.ui.Ui;

import java.text.ParseException;

public class DetectAnomaliesCommand extends Command {

    public DetectAnomaliesCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException, ParseException {

    }

    @Override
    public boolean isExit() {
        return false;
    }
}
