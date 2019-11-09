package javacake.unused;

import javacake.commands.Command;
import javacake.exceptions.CakeException;
import javacake.Logic;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

public class ViewScheduleCommand extends Command {
    public ViewScheduleCommand(String str) {
        type = CmdType.VIEWSCH;
        input = str;
    }

    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {
        if (input.length() == 12) {
            throw new CakeException("     ☹ OOPS!!! The description of a viewschedule cannot be empty.");
        }
        input = input.substring(13);
        /*
        Date currDate = getDate(input);
        ArrayList<Task> scheduleList = new ArrayList<>();

        Calendar calendar = GregorianCalendar.getInstance();
        int currDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currMonth = calendar.get(Calendar.MONTH);
        int currYear = calendar.get(Calendar.YEAR);

        for (Task task : progressStack.getData()) {
            if (task.getTaskType() == Task.TaskType.EVENT || task.getTaskType() == Task.TaskType.DEADLINE) {
                calendar.setTime(task.getDateTime());
                int taskDay = calendar.get(Calendar.DAY_OF_MONTH);
                int taskMonth = calendar.get(Calendar.MONTH);
                int taskYear = calendar.get(Calendar.YEAR);
                if (taskYear == currYear && taskMonth == currMonth && taskDay == currDay) {
                    scheduleList.add(task);
                }
            }
        }
        sortTasksByDate(scheduleList);
        String outputDate = new SimpleDateFormat("dd MMM yyyy").format(currDate);
        if (scheduleList.isEmpty()) {
            ui.showMessage("No tasks on " + outputDate);
        } else {
            ui.showMessage("Here are your tasks for " + outputDate);
            int counter = 1;
            for (Task task : scheduleList) {
                String output = counter + ". " + task.getFullString();
                ui.showMessage(output);
            }
        }
        */
        return "";
    }

}
