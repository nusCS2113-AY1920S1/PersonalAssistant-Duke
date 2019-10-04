package duke.command;

import duke.exceptions.DukeException;
import duke.storage.FileHandling;
import duke.tasks.TaskList;
import duke.ui.Ui;

public class ListCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, FileHandling storage) throws DukeException {
        ui.printList(tasks.getAllTasks());
    }
}
