package duke.command;

import duke.storage.FileHandling;
import duke.tasks.TaskList;
import duke.ui.Ui;

public class ByeCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, FileHandling storage) {
        isExit = true;
        ui.exitDuke();
    }
}