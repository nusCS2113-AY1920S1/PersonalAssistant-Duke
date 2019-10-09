package seedu.duke.task.command;

import seedu.duke.Duke;
import seedu.duke.task.entity.TaskList;
import seedu.duke.common.command.Command;

/**
 * Add a task which do after another task.
 */
public class TaskDoAfterCommand extends Command {

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
    public TaskDoAfterCommand(TaskList taskList, int itemNumber, String doAfterDescription) {
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
        responseMsg = msg;
        Duke.getUI().showResponse(msg);
        return true;
    }
}
