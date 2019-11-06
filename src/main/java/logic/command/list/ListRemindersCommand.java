package logic.command.list;

import logic.command.Command;
import logic.command.CommandOutput;
import model.Model;
import model.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

//@@author AugGust
public class ListRemindersCommand extends Command {

    public static final String EMPTY_REMINDERS_LIST = "There are currently no reminders set.";
    private static final String SUCCESS_MESSAGE = "These are your reminders:\n";

    @Override
    public CommandOutput execute(Model model) {
        ArrayList<Task> tasks = model.getTaskList();
        ArrayList<Task> reminderTasks = new ArrayList<Task>();

        for (int i = 0; i < tasks.size(); i++)  {
            if (tasks.get(i).getReminder() != null) {
                reminderTasks.add(tasks.get(i));
            }
        }

        String reminders = "";

        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd-MM-yyyy HH:mm'H'");
        for (int i = 0; i < reminderTasks.size(); i++)  {
            reminders += "" + (i + 1) + ". At " + sdf.format(reminderTasks.get(i).getReminder()) + ":\n";
            reminders += reminderTasks.get(i).toString() + '\n';
        }

        if (reminderTasks.size() == 0) {
            return new CommandOutput(EMPTY_REMINDERS_LIST);
        } else {
            return new CommandOutput(SUCCESS_MESSAGE + reminders);
        }
    }
}
