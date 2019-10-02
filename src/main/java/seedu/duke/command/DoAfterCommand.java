package seedu.duke.command;

import seedu.duke.Duke;
import seedu.duke.TaskList;

/**
 * Add a task which do after another task.
 */
public class DoAfterCommand extends Command {

    private TaskList taskList;
    private int itemNumber;
    private String doAfterDescription;

    /**
     * Instantiation of do after command.
     *
     * @param taskList           list of tasks.
     * @param itemNumber         index of task.
     * @param doAfterDescription of task.
     */
    public DoAfterCommand(TaskList taskList, int itemNumber, String doAfterDescription) {
        this.taskList = taskList;
        this.itemNumber = itemNumber;
        this.doAfterDescription = doAfterDescription;
    }

    /**
     * Set do after task.
     *
     * @return true.
     */
    @Override
    public boolean execute() {
        taskList.get(itemNumber - 1).setDoAfterDescription(doAfterDescription);
        String msg = "Do after task " + doAfterDescription + " has been added to task " + itemNumber;
        Duke.getUI().showResponse(msg);
        return true;
    }
}
