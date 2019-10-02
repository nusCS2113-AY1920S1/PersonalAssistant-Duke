package commands;

import tasks.TaskList;
import controlpanel.Ui;
import controlpanel.Storage;

/**
 * The command aims to search the tasks with the keyword.
 */
public class SearchCommand extends Command {

    private String keyword;

    /**
     * The constructor which initialize the search command object.
     * @param string The keyword.
     */
    public SearchCommand(String string) {
        keyword = string;
    }

    /**
     * This method labels whether this command means ceasing the overall program.
     * @return Whether this command means ceasing the overall program.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * This method executes the search command. It list down all the tasks which contains.
     * the given keyword.
     * @param tasks The task list object to interact with the checklist.
     * @param ui To print something needed in user interface.
     * @param storage To re-save the data in local disk if necessary.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        int count = 1;
        for (int i = 1;i <= tasks.lengthOfList();i++) {
            if (tasks.getTask(i - 1).getDescription().contains(keyword)) {
                ui.appendToOutput(" " + count++ + "." + tasks.getTask(i - 1).toString() + "\n");
            }
        }
    }
}
