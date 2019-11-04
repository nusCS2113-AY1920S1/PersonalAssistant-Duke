package duke.logic.command;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

/**
 * duke.logic.parser.command.FindCommand which executes the procedure for searching for duke.task.Task objects in the
 * duke.tasklist.TaskList which match the
 * keyword given by the user and shows the user a list of matching Tasks or else informs the user that no
 * matching duke.task.Task objects were found
 */
public class FindCommand extends Command {
    Optional<String> filter;
    private String keyword;
    private Command listCommand;

    /**
     * Constructor for FindCommand
     * Prints all the tasks which match the given keyword and filter
     *
     * @param keyword keyword used to search for matching tasks
     * @param filter  filter for each task
     */
    public FindCommand(String keyword, Optional<String> filter) {
        this.keyword = keyword;
        this.filter = filter;
        this.listCommand = new ListCommand(Optional.empty());
    }

    /**
     * executes the finding of all matching tasks and prints the corresponding tasks
     *
     * @param tasks   TaskList of all of user's tasks
     * @param ui      Ui handling user interaction
     * @param storage Storage handling saving and loading of TaskList
     * @throws ParseException NA
     * @throws IOException    NA
     * @throws DukeException  NA
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ParseException, IOException, DukeException {
        if (tasks.size() == 0) {
            ui.showLine("You have no tasks in your list! :-)");
        } else {
            ArrayList<Task> filteredTaskList = tasks.getList(filter);
            ArrayList<Task> foundTasksTemp = new ArrayList<Task>();
            for (int i = 0; i < filteredTaskList.size(); i++) {
                Task currentTask = filteredTaskList.get(i);
                if (currentTask.getDescription().contains(keyword)) {
                    foundTasksTemp.add(currentTask);
                }
            }
            TaskList foundTasks = new TaskList(foundTasksTemp);
            if (foundTasks.size() == 0) {
                ui.showLine("I'm sorry we found no tasks matching that description!");
            } else {
                listCommand.execute(foundTasks, ui, storage);
            }
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
    public void savePrevState(TaskList tasks, UndoStack undoStack) {

    }
}
