package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.util.ArrayList;

/**
 * duke.command.ListCommand class which executes the command of displaying the duke.tasklist.TaskList to the user
 */
public class ListCommand extends Command {
    private String mode;
    private final String defaultMode = "DEFAULT";

    public ListCommand() {
        mode = defaultMode;
    }

    public ListCommand(String modeInformation) {
        mode = modeInformation;
    }

    public String getMode() {
        return mode;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        switch (mode) {
            case "DEFAULT":
                if (tasks.size() > 0) {
                    ui.showLine("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        ui.showEntry(i + 1, tasks.get(i));
                    }
                } else {
                    ui.showLine("There are no tasks in your list.");
                }
                break;
            case "priority":
                tasks.priorityPrint(ui);
                break;
            case "day":
                tasks.dayViewPrint(ui);
                break;
            case "week":
                tasks.weekViewPrint(ui);
                break;
            default:
                throw new DukeException("The description of list is invalid");
        }

    }

    @Override
    public boolean isExit() {
        return false;
    }
}