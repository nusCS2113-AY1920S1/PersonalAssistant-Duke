package duke.logic.command;

import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.tasklist.TaskList;
import duke.ui.TaskListPrinter;
import duke.ui.Ui;

/**
 * duke.logic.parser.command.ListCommand class which executes the command of displaying the
 * duke.tasklist.TaskList to the user
 */
public class ListCommand extends Command {
    private String mode;
    private Optional<String> filter;

    /**
     * Constructor for ListCommand
     * Creates a ListCommand with no special sorted order to be printed in
     * If there is a filter present, only tasks fulfilling the filter predicate will be printed
     *
     * @param filter filter for each task
     */
    public ListCommand(Optional<String> filter) {
        mode = "DEFAULT";
        this.filter = filter;
    }

    /**
     * Constructor for ListCommand
     * Creates a ListCommand with a special sorted order to be printed in
     * If there is a filter present, only tasks fulfilling the filter predicate will be printed
     *
     * @param modeInformation information of the sorted order
     * @param filter          filter for each task
     */
    public ListCommand(String modeInformation, Optional<String> filter) {
        mode = modeInformation;
        this.filter = filter;
    }

    /**
     * Executes the printing of the TaskList based on the user specifications
     * If there is a specified sort order of printing, the sequence will be printed in the specified order
     *
     * @param list    TaskList containing all of user's tasks
     * @param ui      Ui handling user interactions
     * @param storage Storage handling saving and loading of TaskList
     * @throws DukeException  if specified sort order given is invalid
     * @throws IOException    NA
     * @throws ParseException NA
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws DukeException, IOException, ParseException {
        TaskList tasks;
        if (!filter.isPresent()) {
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

    /**
     * Not applicable for this Command.
     *
     * @param tasks     NA
     * @param undoStack NA
     * @throws DukeException NA
     */
    @Override
    public void savePrevState(TaskList tasks, UndoStack undoStack) throws DukeException {

    }
}
