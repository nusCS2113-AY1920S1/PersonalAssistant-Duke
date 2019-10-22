package seedu.duke.task.command;

import seedu.duke.CommandParser;
import seedu.duke.Duke;
import seedu.duke.common.command.Command;
import seedu.duke.task.TaskList;

public class TaskSnoozeCommand extends Command {
    private int index;

    /**
     * Instantiation of the snooze command which can be used to snooze a task.
     *
     * @param index the index of the task to be snoozed
     */
    TaskSnoozeCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the SnoozeCommand to snooze a task for 3 days.
     *
     * @return true
     */
    @Override
    public boolean execute() {
        TaskList taskList = Duke.getModel().getTaskList();
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

