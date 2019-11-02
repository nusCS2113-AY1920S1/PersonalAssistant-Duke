package logic.command.edit;

import common.DukeException;
import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;

import java.util.Date;

public class EditTaskTimeCommand extends Command {

    private static final String SUCCESS_MESSAGE = "The deadline has been changed to: ";
    public static final String INDEX_NOT_IN_TASKlIST_MESSAGE = "Index is not within the task list";
    public int taskIndex;
    public Date newDate;

    public EditTaskTimeCommand(int taskIndex, Date newDate) {
        this.taskIndex = taskIndex;
        this.newDate = newDate;
    }

    //@@author JasonChanWQ
    @Override
    public CommandOutput execute(Model model) throws DukeException {
        if (taskIndex < 1 || taskIndex > model.getTaskListSize()) {
            return new CommandOutput(INDEX_NOT_IN_TASKlIST_MESSAGE);
        } else {
            model.getTaskList().get(taskIndex - 1).setTime(newDate);
            model.save();
            return new CommandOutput(SUCCESS_MESSAGE + newDate);
        }
    }


}
