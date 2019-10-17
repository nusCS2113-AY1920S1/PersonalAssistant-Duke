package seedu.duke.task.command;

import seedu.duke.Duke;
import seedu.duke.CommandParser;
import seedu.duke.task.TaskList;
import seedu.duke.common.command.Command;

public class TaskSnoozeCommand extends Command {
    private TaskList taskList;
    private int index;

    /**
     * Instantiation of the snooze command which can be used to snooze a task.
     *
     * @param taskList the list where snoozed command is looked up
     * @param index    the index of the task to be snoozed
     */
    public TaskSnoozeCommand(TaskList taskList, int index) {
        this.taskList = taskList;
        this.index = index;
    }

    /**
     * Executes the SnoozeCommand to snooze a task for 3 days.
     *
     * @return true
     */
    @Override
    public boolean execute() {
        try {
            String msg = taskList.snoozed(index);
            if (!silent) {
                responseMsg = msg;
                Duke.getUI().showResponse(msg);
            }
            return true;
        } catch (CommandParser.UserInputException e) {
            if (!silent) {
                Duke.getUI().showError(e.getMessage());
            }
            return false;
        }
    }
}

