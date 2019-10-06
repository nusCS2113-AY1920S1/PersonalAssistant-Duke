package seedu.duke.command;

import seedu.duke.Duke;
import seedu.duke.TaskList;

public class ReminderCommand extends Command {
    private int dayLimit = 3; //default limit is 3 days
    private TaskList taskList;

    /**
     * Instantiation of the ReminderCommand which can be used to print all the tasks near.
     *
     * @param taskList the TaskList where the near command is looked up
     * @param dayLimit the maximum number of days from now for a task to be considered as near
     */
    public ReminderCommand(TaskList taskList, int dayLimit) {
        this.taskList = taskList;
        this.dayLimit = dayLimit;
    }

    /**
     * Instantiation of the ReminderCommand which can be used to print all the tasks near. This overload uses
     * the default dayLimit instead.
     *
     * @param taskList the TaskList where the near command is looked up
     */
    public ReminderCommand(TaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * Execute the ReminderCommand to print out all the near tasks.
     *
     * @return true as the command can always be correctly executed
     */
    @Override
    public boolean execute() {
        TaskList nearTasks = taskList.findNear(dayLimit);
        String msg = "";
        if (nearTasks.size() == 0) {
            msg += "There is no near event or deadline. ";
        } else {
            msg += "There are near events or deadlines within " + dayLimit + " days: \n";
            msg += nearTasks.toString();
        }
        responseMsg = msg;
        Duke.getUI().showResponse(msg);
        return true;
    }
}
