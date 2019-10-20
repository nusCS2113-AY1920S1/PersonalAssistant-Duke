package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class ViewReminderCommand extends Command {
    @Override
    public CommandResult commandExecute(TaskList taskList) throws CommandException {
        Comparator<Task> compareByDateTime = Comparator.comparing(Task::getDate);
        ArrayList<Task> currList = taskList.getArrList();
        currList.sort(compareByDateTime);

        String taskReminders = getTaskReminders(currList);

        return new CommandResult(taskReminders,false);
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

        calendar.setTime(currentDate);
        Date dateToday = calendar.getTime();

        for (Task task : currList) {
            if (!task.gethasReminder() && ((task.getEndTime().after(dateToday)
                    && task.getEndTime().before(dateAfter)) || task.gethasReminder())) {
                String taskString = task.toString() + "\n";
                taskReminder.append(taskString);
            }
        }
        return taskReminder.toString();
    }
}
