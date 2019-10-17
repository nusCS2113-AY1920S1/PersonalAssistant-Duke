package duke.command;

import duke.exception.DukeException;
import duke.recipebook.dishlist;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;
import duke.recipebook.dishes;

public class InitCommand extends Command {

    private Boolean status = false;

    public InitCommand() {
        //clears all the amount in dishes
    }

    @Override
    public void execute(dishlist dish, TaskList taskList, Ui ui, Storage storage) throws DukeException {
        //something
    }
}
