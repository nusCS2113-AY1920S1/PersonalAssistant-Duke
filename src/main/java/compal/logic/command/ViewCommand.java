package compal.logic.command;

import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;


/**
 * The ListCommand Class handles all list commands.
 */
public class ViewCommand extends Command {
    private String[] viewargs;

    public ViewCommand(String[] viewArgs) {
        super();
        this.viewargs = viewArgs;
    }

    @Override
    public CommandResult commandExecute(TaskList taskList) {
        Comparator<Task> compareByDateTime = Comparator.comparing(Task::getDate);
        ArrayList<Task> currList = taskList.getArrList();
        currList.sort(compareByDateTime);

        String viewType = viewargs[0];
        String dateInput = viewargs[1];

        String[] dateParts = dateInput.split("/");
        //String day = dateParts[0];
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        String finalList = "";


        switch (viewType) {
        case "/month":
            finalList = displayMonthView(month, year, currList);
            break;
        case "/day":
            finalList = displayDayView(dateInput, currList);
            break;
        default:
            break;
        }
        return new CommandResult(finalList);
    }

    private String displayMonthView(int givenMonth, int givenYear, ArrayList<Task> currList) {
        String[] months = {"", "January", "February", "March", "April", "May", "June",
                              "July", "August", "September", "October", "November", "December"};

        StringBuilder monthlyTask = new StringBuilder("Here are your task for the month of "
                + months[givenMonth] + " " + givenYear + " :\n");

        Calendar cal = Calendar.getInstance();
        for (Task t : currList) {
            if (t.getSymbol().equals("E")) {
                cal.setTime(t.getDateObgDateAndStartTime());
            } else {
                cal.setTime(t.getDateObgDateAndEndTime());
            }

            int taskMonth = cal.get(Calendar.MONTH) + 1;
            int taskYear = cal.get(Calendar.YEAR);

            if (taskMonth == givenMonth && taskYear == givenYear) {
                String taskString = t.toString() + "\n";
                monthlyTask.append(taskString);
            }
        }
        return monthlyTask.toString();
    }

    private String displayDayView(String dateInput, ArrayList<Task> currList) {
        StringBuilder dailyTask = new StringBuilder("Here are your task for the day of " + dateInput + " :\n");

        for (Task t : currList) {
            if (t.getStringDate().equals(dateInput)) {
                String taskString = t.toString() + "\n";
                dailyTask.append(taskString);
            }
        }
        return dailyTask.toString();
    }
}