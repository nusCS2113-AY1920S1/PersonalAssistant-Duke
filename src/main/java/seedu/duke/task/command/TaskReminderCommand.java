package seedu.duke.task.command;

import seedu.duke.Duke;
import seedu.duke.task.TaskList;
import seedu.duke.common.command.Command;

public class TaskReminderCommand extends Command {
    private int dayLimit = 3; //default limit is 3 days

    /**
     * Instantiation of the ReminderCommand which can be used to print all the tasks near.
     *
     * @param dayLimit the maximum number of days from now for a task to be considered as near
     */
    TaskReminderCommand(int dayLimit) {
        this.dayLimit = dayLimit;
    }

    /**
     * Instantiation of the ReminderCommand which can be used to print all the tasks near. This overload uses
     * the default dayLimit instead.
     */
    public TaskReminderCommand() {

    }

    /**
     * Execute the ReminderCommand to print out all the near tasks.
     *
     * @return true as the command can always be correctly executed
     */
    @Override
    public boolean execute() {
        TaskList taskList = Duke.getModel().getTaskList();
        TaskList nearTasks = taskList.findNear(dayLimit);
        String msg = "";
        if (nearTasks.size() == 0) {
            msg += "There is no near event or deadline. ";
        } else {
            msg += "There are near events or deadlines within " + dayLimit + " days: \n";
            msg += nearTasks.toString();
        }
        responseMsg = msg;
        if (!silent) {
            Duke.getUI().showResponse(msg);
        }
        return true;
    }
}
