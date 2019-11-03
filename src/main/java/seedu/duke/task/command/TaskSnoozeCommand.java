package seedu.duke.task.command;

import seedu.duke.common.parser.CommandParseHelper;
import seedu.duke.common.command.Command;
import seedu.duke.common.model.Model;
import seedu.duke.task.TaskList;
import seedu.duke.ui.UI;

public class TaskSnoozeCommand extends Command {
    private int index;
    private int duration;

    /**
     * Instantiation of the snooze command which can be used to snooze a task.
     *
     * @param index the index of the task to be snoozed
     */
    public TaskSnoozeCommand(int index, int duration) {
        this.index = index;
        this.duration = duration;

    }

    /**
     * Executes the SnoozeCommand to snooze a task for 3 days.
     *
     * @return true
     */
    @Override
    public boolean execute(Model model) {
        TaskList taskList = model.getTaskList();
        try {
            String msg = taskList.snoozed(index, duration);
            if (!silent) {
                responseMsg = msg;
                UI.getInstance().showResponse(msg);
            }
            return true;
        } catch (CommandParseHelper.CommandParseException e) {
            if (!silent) {
                UI.getInstance().showError(e.getMessage());
            }
            return false;
        }
    }
}

