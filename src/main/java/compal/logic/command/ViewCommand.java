package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import compal.ui.CalenderUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * View the task in day,week or month format.
 */
public class ViewCommand extends Command {
    private static final String MESSAGE_UNABLE_TO_EXECUTE = "Unable to execute command!";
    private String[] viewargs;
    private CalenderUtil calenderUtil;
    private String viewType;
    private String dateInput;
    private String type;

    /**
     * Generate constructor for viewCommand.
     *
     * @param viewType  the view Type
     * @param dateInput the date of input
     */
    public ViewCommand(String viewType, String dateInput) {
        this.viewType = viewType;
        this.dateInput = dateInput;
        this.type = "";
        calenderUtil = new CalenderUtil();
    }

    /**
     * override.
     *
     * @param typeToShow the type to be display only
     */
    public ViewCommand(String viewType, String dateInput, String typeToShow) {
        this.viewType = viewType;
        this.dateInput = dateInput;


        if (typeToShow.equals("deadline")) {
            this.type = "D";
        } else if (typeToShow.equals("event")) {
            this.type = "E";
        }


        calenderUtil = new CalenderUtil();
    }

    @Override
    public CommandResult commandExecute(TaskList taskList) throws CommandException {
        ArrayList<Task> currList = taskList.getArrList();


        String[] dateParts = dateInput.split("/");

        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        String finalList = "";


        switch (viewType) {
        case "/month":
            finalList = displayMonthView(month, year, currList);
            break;
        case "/week":
            finalList = displayWeekView(dateInput, currList);
            break;
        case "/day":
            finalList = finalList + ("Your daily schedule for " + dateInput + " :\n");
            finalList = finalList + displayDayView(dateInput, currList);
            calenderUtil.dateViewRefresh(dateInput);
            break;
        default:
            break;
        }
        return new CommandResult(finalList, false);
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

        int[] days = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        StringBuilder monthlyTask = new StringBuilder("Your monthly schedule for "
            + months[givenMonth] + " " + givenYear + " :\n");

        for (int i = 1; i <= days[givenMonth]; i++) {
            if (i < 9) {
                monthlyTask.append(displayDayView("0" + i + "/" + givenMonth + "/" + givenYear, currList));
            } else {
                monthlyTask.append(displayDayView(i + "/" + givenMonth + "/" + givenYear, currList));
            }

        }
        return monthlyTask.toString();
    }

    /**
     * return all task for a given week.
     *
     * @param dateInput the date of task input.
     * @param currList  the curr taskList of task.
     * @return string output
     */
    private String displayWeekView(String dateInput, ArrayList<Task> currList) throws CommandException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date week;

        try {
            week = dateFormat.parse(dateInput);
        } catch (ParseException e) {
            throw new CommandException(MESSAGE_UNABLE_TO_EXECUTE);
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(week);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        int daysInWeek = 7;
        String[] dates = new String[daysInWeek];
        StringBuilder[] dailyTask = new StringBuilder[daysInWeek];

        for (int i = 0; i < daysInWeek; i++) {
            dates[i] = dateFormat.format(cal.getTime());//Date of Monday of current week
            dailyTask[i] = new StringBuilder();
            cal.add(Calendar.DATE, 1);
        }

        cal.setTime(week);

        StringBuilder weeklyTask = new StringBuilder("Your weekly schedule from "
            + dates[0] + " to " + dates[6] + " :\n");

        for (int i = 0; i < daysInWeek; i++) {
            dailyTask[i].append(displayDayView(dates[i], currList));
            weeklyTask.append(dailyTask[i]);
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

        StringBuilder allTask = new StringBuilder();

        for (Task t : currList) {
            if (!type.equals("")) {
                if (!t.getSymbol().equals(type)) {
                    continue;
                }
            }
            if (t.getStringDate().equals(dateInput)) {
                allTask.append(getAsStringView(t));
            }
        }

        if (allTask.length() == 0) {
            allTask.append("\n\n");
        }

        String header = "\n" + "_".repeat(65) + "\n"
            + " ".repeat((100)) + dateInput + "\n";
        return header + allTask.toString();

    }

    private String getAsStringView(Task t) {


        StringBuilder taskDetails = new StringBuilder();

        String rightArrow = "\u2192";

        boolean isDone = t.getisDone();
        String status;
        if (isDone) {
            status = "\u2713";
        } else {
            status = "\u274C";
        }

        String startTime = t.getStringStartTime();
        String endTime = t.getStringEndTime();

        if (startTime.equals("-")) {
            taskDetails.append("  Due: ").append(endTime)
                .append("\n");

        } else {
            taskDetails.append("  Time: ").append(startTime)
                .append(" ").append(rightArrow)
                .append(" ").append(endTime)
                .append("\n");
        }

        int taskId = t.getId();
        Task.Priority priority = t.getPriority();

        taskDetails
            .append("  [Task ID:").append(taskId).append("] ")
            .append("[Priority:").append(priority).append("]\n");

        String taskSymbol = t.getSymbol();
        String taskDescription = t.getDescription();
        taskDetails.append("  [").append(taskSymbol).append("] ")
            .append("[").append(status).append("] ")
            .append(taskDescription).append("\n\n");

        return taskDetails.toString();
    }

}