package seedu.duke.command;

import seedu.duke.task.DetectAnomalies;
import seedu.duke.task.TaskList;
import seedu.duke.ui.Ui;

/**
 * Command that adds task to list.
 */
public class AddCommand extends Command {
    
    /**
     * Contains task type and description.
     */
    private String[] taskInfo;

    /**
     * Initializes taskInfo.
     *
     * @param taskInfo the info of the task to add.
     */
    public AddCommand(String[] taskInfo) {
        this.taskInfo = taskInfo;
    }
    
    /**
     * Adds task of type and description inside taskInfo.
     *
     * @param list list of tasks to add to.
     */
    public void execute(TaskList list) {
        if (this.taskInfo.length == 1) {
            Ui ui = new Ui();
            ui.empty_description_error();
            return;
        }
        list.add(this.taskInfo[0], this.taskInfo[1]);
    }
}
