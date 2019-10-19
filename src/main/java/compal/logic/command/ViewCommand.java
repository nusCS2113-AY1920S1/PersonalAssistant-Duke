package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import compal.ui.CalenderUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;


/**
 * View the task in day,week or month format.
 */
public class ViewCommand extends Command {
    private String[] viewargs;
    private CalenderUtil calenderUtil;
    private static final String MESSAGE_UNABLE_TO_EXECUTE = "Unable to execute command!";

    /**
     * Generate constructor for viewCommand.
     *
     * @param viewArgs the arguments
     */
    public ViewCommand(String[] viewArgs) {
        super();
        this.viewargs = viewArgs;
        calenderUtil = new CalenderUtil();
    }

    @Override
    public CommandResult commandExecute(TaskList taskList) throws CommandException {
        ArrayList<Task> currList = taskList.getArrList();

        String viewType = viewargs[0];
        String dateInput = viewargs[1];

        String[] dateParts = dateInput.split("/");

        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        String finalList = "";


        switch (viewType) {
        case "/month":
            finalList = displayMonthView(month, year, currList);
            break;
        case "/week":
            finalList = displayWeekView(dateInput, year, currList);
            break;
        case "/day":
            finalList = displayDayView(dateInput, currList);
            break;
        default:
            break;
        }
        return new CommandResult(finalList);
    }


    /**
     * return all task for a given month.
     *
     * @param givenMonth the month input by user.
     * @param givenYear  the year input by user.
     * @param currList   the curr taskList of task.
     * @return stringo output
     */
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

    /**
     * return all task for a given week.
     *
     * @param dateInput the date of task input.
     * @param givenYear the given year.
     * @param currList  the curr taskList of task.
     * @return string output
     */
    private String displayWeekView(String dateInput, int givenYear, ArrayList<Task> currList) throws CommandException {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat =  new SimpleDateFormat("dd/MM/yyyy");
        Date week;

        String dates[] = new String[7];

        try {
            week = dateFormat.parse(dateInput);
        } catch (ParseException e) {
            throw new CommandException(MESSAGE_UNABLE_TO_EXECUTE);
        }

        cal.setTime(week);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);


        for(int i = 0 ; i < 7; i ++){
            dates[i] = dateFormat.format(cal.getTime());//Date of Monday of current week
            cal.add(Calendar.DATE, 1);
        }

        int inputDayOfWeek = cal.get(Calendar.WEEK_OF_YEAR);

        StringBuilder weeklyTask = new StringBuilder("Here are your task from " + dates[0] + " - " + dates[6] + " :\n");
        for (Task t : currList) {
            if (t.getSymbol().equals("E")) {
                cal.setTime(t.getDateObgDateAndStartTime());
            } else {
                cal.setTime(t.getDateObgDateAndEndTime());
            }

            int taskDayOfWeek = cal.get(Calendar.WEEK_OF_YEAR);
            int taskYear = cal.get(Calendar.YEAR);


            if (taskDayOfWeek == inputDayOfWeek && taskYear == givenYear) {
                String taskString = t.toString() + "\n";
                weeklyTask.append(taskString);
            }
        }

        return weeklyTask.toString();
    }


    /**
     * return all task for a given day.
     *
     * @param dateInput the date of task input.
     * @param currList  the curr taskList of task.
     * @return string output
     */
    private String displayDayView(String dateInput, ArrayList<Task> currList) {
        StringBuilder dailyTask = new StringBuilder("Here are your task for the day of " + dateInput + " :\n");

        for (Task t : currList) {
            if (t.getStringDate().equals(dateInput)) {
                String taskString = t.toString() + "\n";
                dailyTask.append(taskString);
            }
        }
        calenderUtil.dateViewRefresh(dateInput);
        return dailyTask.toString();
    }
}