package logic.command.edit;

import common.DukeException;
import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;

import java.util.Date;

//@@author JasonChanWQ

public class EditTaskDateTimeCommand extends Command {

    private static final String SUCCESS_MESSAGE = "The deadline has been changed to: ";
    public static final String INDEX_NOT_IN_TASKlIST_MESSAGE = "Index is not within the task list";
    public static final String NEW_DATETIME_IS_BEHIND_CURRENT_TIME_MESSAGE = "The input DateTime has already passed!";
    public int taskIndex;
    public Date newDate;

    public EditTaskDateTimeCommand(int taskIndex, Date newDate) {
        this.taskIndex = taskIndex;
        this.newDate = newDate;
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        Date currentDate = new Date();

        if (!model.isInTaskList(taskIndex)) {
            return new CommandOutput(INDEX_NOT_IN_TASKlIST_MESSAGE);
        } else if (newDate.compareTo(currentDate) < 0) {
            return new CommandOutput(NEW_DATETIME_IS_BEHIND_CURRENT_TIME_MESSAGE);
        } else {
            model.getTaskList().get(taskIndex - 1).setTime(newDate);
            model.getTaskList().get(taskIndex - 1).setReminder(null);

            model.save();
            return new CommandOutput(SUCCESS_MESSAGE + newDate);
        }
    }
}
