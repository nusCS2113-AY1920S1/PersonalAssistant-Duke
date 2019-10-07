package seedu.duke.task.command;

import seedu.duke.Duke;
import seedu.duke.Parser;
import seedu.duke.task.entity.TaskList;
import seedu.duke.common.command.Command;

public class TaskSnoozeCommand extends Command {
    private TaskList taskList;
    private int index;

    public TaskSnoozeCommand(TaskList taskList, int index) {
        this.taskList = taskList;
        this.index = index;
    }

    @Override
    public boolean execute() {
        try {
            String msg = taskList.snoozed(index);
            if (!silent) {
                responseMsg = msg;
                Duke.getUI().showResponse(msg);
            }
            return true;
        } catch (Parser.UserInputException e) {
            if (!silent) {
                Duke.getUI().showError(e.getMessage());
            }
            return false;
        }
    }
}

