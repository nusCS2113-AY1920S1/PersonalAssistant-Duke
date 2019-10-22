package javacake.commands;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import javacake.exceptions.DukeException;
import javacake.ProgressStack;
import javacake.storage.Profile;
import javacake.ui.Ui;
import javacake.storage.Storage;
import javacake.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ViewScheduleCommand extends Command {
    public ViewScheduleCommand(String str) {
        type = CmdType.VIEWSCH;
        input = str;
    }

    @Override
    public String execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) throws DukeException {
        if (input.length() == 12) {
            throw new DukeException("     ☹ OOPS!!! The description of a viewschedule cannot be empty.");
        }
        input = input.substring(13);
        Date currDate = getDate(input);
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(currDate);
        int currDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currMonth = calendar.get(Calendar.MONTH);
        int currYear = calendar.get(Calendar.YEAR);

        ArrayList<Task> scheduleList = new ArrayList<>();
        /*for (Task task : progressStack.getData()) {
            if (task.getTaskType() == Task.TaskType.EVENT || task.getTaskType() == Task.TaskType.DEADLINE) {
                calendar.setTime(task.getDateTime());
                int taskDay = calendar.get(Calendar.DAY_OF_MONTH);
                int taskMonth = calendar.get(Calendar.MONTH);
                int taskYear = calendar.get(Calendar.YEAR);
                if (taskYear == currYear && taskMonth == currMonth && taskDay == currDay) {
                    scheduleList.add(task);
                }
            }
        }*/
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
        return "";
    }

    static void sortTasksByDate(ArrayList<Task> scheduleList) {
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

    private Date getDate(String input) throws DukeException {
        Date date;
        try {
            Parser parser = new Parser();
            List<DateGroup> groups = parser.parse(input);
            date = groups.get(0).getDates().get(0);
        } catch (Exception e) {
            throw new DukeException("   Date cannot be parsed: " + input);
        }
        return date;
    }
}
