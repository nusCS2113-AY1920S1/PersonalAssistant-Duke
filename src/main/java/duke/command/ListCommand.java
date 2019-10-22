package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.TaskListPrinter;
import duke.ui.Ui;

import java.util.ArrayList;
import java.util.Optional;

/**
 * duke.command.ListCommand class which executes the command of displaying the duke.tasklist.TaskList to the user
 */
public class ListCommand extends Command {
    private String mode;
    private Optional<String> filter;

    public ListCommand(Optional<String> filter) {
        mode = "DEFAULT";
        this.filter = filter;
    }

    public ListCommand(String modeInformation, Optional<String> filter) {
        mode = modeInformation;
        this.filter = filter;
    }

    public String getMode() {
        return mode;
    }

    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws DukeException {
        TaskList tasks;
        if (filter.isEmpty()) {
            tasks = list;
        } else {
            tasks = new TaskList(list, filter);
        }
        switch (mode) {
            case "DEFAULT":
                if (tasks.size() > 0) {
                    TaskListPrinter.print(ui, tasks);
                } else {
                    ui.showLine("There are no tasks in your list.");
                }
                break;
            case "priority":
                tasks = tasks.priorityView();
                if (tasks.size() > 0) {
                    TaskListPrinter.print(ui, tasks);
                } else {
                    ui.showLine("There are no tasks in your list.");
                }
                break;
            case "day": {
                tasks = tasks.dayView();
                if (tasks.size() == 0) {
                    ui.showLine("Congratulations, you have no tasks for the day! Take a break, have a kit kat");
                } else {
                    TaskListPrinter.print(ui, tasks);
                }
                break;
            }
            case "week":
                tasks = tasks.weekView();
                if (tasks.size() == 0) {
                    ui.showLine("Congratulations, you have no tasks for the week! Take a break, have a kit kat");
                } else {
                    TaskListPrinter.print(ui, tasks);
                }
                break;
            case "undone":
                tasks = tasks.undoneView();
                if (tasks.size() == 0) {
                    ui.showLine("Congratulations, you have no undone tasks! Take a break, have a kit kat");
                } else {
                    TaskListPrinter.print(ui, tasks);
                }
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