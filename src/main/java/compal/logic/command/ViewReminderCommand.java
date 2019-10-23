package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ViewReminderCommand extends Command {

    public static final String MESSAGE_USAGE = "view-reminder\n\t"
            + "Format: view-reminder\n\n\t"
            + "This command will show all tasks with reminder on\n"
            + "Examples:\n\t"
            + "view-reminder\n\t\t"
            + "show all tasks with reminder on";
    private static final String MESSAGE_UNABLE_TO_EXECUTE = "Unable to execute command!";

    @Override
    public CommandResult commandExecute(TaskList taskList) throws CommandException {
        String taskReminders;
        try {
            taskReminders = getTaskReminders(taskList.getArrList());
        } catch (Exception e) {
            throw new CommandException(MESSAGE_UNABLE_TO_EXECUTE);
        }
        return new CommandResult(taskReminders, false);
    }


    /**
     * Returns task reminders.
     *
     * @param currList List of tasks
     * @return String of tasks
     */
    private String getTaskReminders(ArrayList<Task> currList) {
        StringBuilder taskReminder = new StringBuilder("Here are your tasks:\n");

        Date currentDate = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, 7);
        Date dateAfter = calendar.getTime();

        /*
        calendar.setTime(currentDate);
        Date dateToday = calendar.getTime();
         */

        for (Task task : currList) {
            if (!task.getisDone() && (task.getEndTime().before(dateAfter) || task.gethasReminder())) {
                String taskString = task.toString() + "\n";
                taskReminder.append(taskString);
            }
        }
        return taskReminder.toString();
    }
}
