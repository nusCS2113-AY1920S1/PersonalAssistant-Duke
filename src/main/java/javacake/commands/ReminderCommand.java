package javacake.commands;

import javacake.Logic;
import javacake.exceptions.CakeException;
import javacake.storage.StorageManager;
import javacake.ui.Ui;
import javacake.tasks.Task;

import java.util.ArrayList;

public class ReminderCommand extends Command {

    /**
     * Constructor for ReminderCommand.
     * Checks that no parameters are included.
     * @param inputCommand Reminder command from user.
     * @throws CakeException If other parameter is appended to command.
     */
    public ReminderCommand(String inputCommand) throws CakeException {
        checksParam(inputCommand);
        type = CmdType.REMIND;
    }

    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) {
        ArrayList<Task> deadlineList = storageManager.storage.getData();
        StringBuilder stringBuilder = new StringBuilder();
        sortTasksByDate(deadlineList);
        int idx = 1;
        if (deadlineList.size() > 0) {
            //ui.showMessage("Here are the upcoming Deadlines:");
            stringBuilder.append("~~Upcoming Deadlines!~~\n");
            for (Task task : deadlineList) {
                stringBuilder.append(idx++).append(".");
                stringBuilder.append(task.getFullString()).append("\n");
                //ui.showMessage(stringBuilder.toString());
            }
        } else {
            //ui.showError("You have no deadlines as of now.");
            stringBuilder.append("You have no deadlines as of now.\n");
        }
        return stringBuilder.toString();
    }

    private static void sortTasksByDate(ArrayList<Task> scheduleList) {
        scheduleList.sort((o1, o2) -> {
            if (o1.getDateTime() == null) {
                return 1;
            } else if (o2.getDateTime() == null) {
                return -1;
            }
            if (o1.getDateTime().before(o2.getDateTime())) {
                return -1;
            } else if (o1.getDateTime().after(o2.getDateTime())) {
                return 1;
            }
            return 0;
        });
    }
}
