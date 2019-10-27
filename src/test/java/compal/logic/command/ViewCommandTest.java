package compal.logic.command;

import compal.commons.CompalUtils;
import compal.model.tasks.Deadline;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//@@author SholihinK
class ViewCommandTest {

    private ArrayList<Task> taskArrListMain = new ArrayList<>();
    private TaskList taskListMain = new TaskList();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        Event event1 = new Event("CS2105 Lecture", Task.Priority.medium, "01/10/2019", "01/10/2019", "1400", "1500");
        event1.markAsDone();
        Event event2 = new Event("CS2106 Tut", Task.Priority.low, "02/10/2019", "02/10/2019", "1400", "1500");
        Event event3 = new Event("CS2113T Lab", Task.Priority.low, "03/10/2019", "03/10/2019", "1400", "1500");
        Event event4 = new Event("CS2101 Sect", Task.Priority.low, "03/10/2019", "03/10/2019", "1400", "1500");
        Event event5 = new Event("CS2101 Sect", Task.Priority.low, "05/10/2019", "05/10/2019", "1400", "1500");
        Event event6 = new Event("CS2101 rt", Task.Priority.low, "06/10/2019", "06/10/2019", "1400", "1500");

        taskArrListMain.add(event1);
        taskArrListMain.add(event2);
        taskArrListMain.add(event3);
        taskArrListMain.add(event4);
        taskArrListMain.add(event5);
        taskArrListMain.add(event6);

        Deadline deadline1 = new Deadline("Deadline 1", Task.Priority.high, "03/10/2019", "1500");
        deadline1.markAsDone();
        Deadline deadline2 = new Deadline("CS2106 Assignment", Task.Priority.low, "04/10/2019", "1500");
        Deadline deadline3 = new Deadline("Deadline 3", Task.Priority.high, "05/10/2019", "1500");
        Deadline deadline4 = new Deadline("Deadline 4", Task.Priority.high, "05/10/2019", "1500");

        taskArrListMain.add(deadline1);
        taskArrListMain.add(deadline2);
        taskArrListMain.add(deadline3);
        taskArrListMain.add(deadline4);

        this.taskListMain.setArrList(taskArrListMain);
    }

    @Test
    void execute_viewWeek_success() {
        Calendar currentDay = Calendar.getInstance();
        String finalDate = CompalUtils.dateToString(currentDay.getTime());
        String viewType = "week";
        String expected;
        String tested;


        String type = "";
        expected = new ViewCommand(viewType, finalDate).commandExecute(taskListMain).feedbackToUser;
        tested = displayWeekView(finalDate, taskArrListMain, type);
        Assertions.assertEquals(expected, tested);

        type = "D";
        finalDate = "01/10/2019";
        expected = new ViewCommand(viewType, finalDate, "deadline").commandExecute(taskListMain).feedbackToUser;
        tested = displayWeekView(finalDate, taskArrListMain, type);
        Assertions.assertEquals(expected, tested);

        type = "E";
        finalDate = "01/10/2019";
        expected = new ViewCommand(viewType, finalDate, "event").commandExecute(taskListMain).feedbackToUser;
        tested = displayWeekView(finalDate, taskArrListMain, type);
        Assertions.assertEquals(expected, tested);
    }

    @Test
    void execute_viewMonth_success() {
        String date = "01/10/2019";
        int month = 10;
        int year = 2019;
        String viewType = "month";
        String expected;
        String tested;

        String type = "";
        expected = new ViewCommand(viewType, date).commandExecute(taskListMain).feedbackToUser;
        tested = displayMonthView(month, year, taskArrListMain, type);
        Assertions.assertEquals(expected, tested);

        type = "E";
        expected = new ViewCommand(viewType, date, "event").commandExecute(taskListMain).feedbackToUser;
        tested = displayMonthView(month, year, taskArrListMain, type);
        Assertions.assertEquals(expected, tested);

        type = "D";
        expected = new ViewCommand(viewType, date, "deadline").commandExecute(taskListMain).feedbackToUser;
        tested = displayMonthView(month, year, taskArrListMain, type);
        Assertions.assertEquals(expected, tested);
    }

    private String displayMonthView(int givenMonth, int givenYear, ArrayList<Task> currList, String type) {
        String[] months = {"", "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};

        YearMonth yearMonthObject = YearMonth.of(givenYear, givenMonth);
        int days = yearMonthObject.lengthOfMonth(); //28

        StringBuilder monthlyTask = new StringBuilder("Your monthly schedule for "
            + months[givenMonth] + " " + givenYear + " :\n");

        for (int i = 1; i <= days; i++) {
            if (i < 9) {
                monthlyTask.append(displayDayView("0" + i + "/" + givenMonth + "/" + givenYear, currList, type));
            } else {
                monthlyTask.append(displayDayView(i + "/" + givenMonth + "/" + givenYear, currList, type));
            }
        }
        return monthlyTask.toString();
    }

    private String displayWeekView(String dateInput, ArrayList<Task> currList, String type) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Calendar cal = Calendar.getInstance();
        cal.setTime(CompalUtils.stringToDate(dateInput));

        int daysInWeek = 7;
        String[] dates = new String[daysInWeek];
        StringBuilder[] dailyTask = new StringBuilder[daysInWeek];

        for (int i = 0; i < daysInWeek; i++) {
            dates[i] = dateFormat.format(cal.getTime());//Date of Monday of current week
            //calenderUtil.dateViewRefresh(dates[i]);
            dailyTask[i] = new StringBuilder();
            cal.add(Calendar.DATE, 1);
        }

        StringBuilder weeklyTask = new StringBuilder("Your weekly schedule from "
            + dates[0] + " to " + dates[6] + " :\n");

        for (int i = 0; i < daysInWeek; i++) {
            dailyTask[i].append(displayDayView(dates[i], currList, type));
            weeklyTask.append(dailyTask[i]);

        }
        return weeklyTask.toString();
    }

    private String displayDayView(String dateInput, ArrayList<Task> currList, String type) {

        StringBuilder allTask = new StringBuilder();

        for (Task t : currList) {
            if (!"".equals(type) && !t.getSymbol().equals(type)) {
                continue;
            }

            if (t.getStringMainDate().equals(dateInput)) {
                allTask.append(getAsStringView(t));
            }
        }

        if (allTask.length() == 0) {
            allTask.append("\n\n");
        }

        Date givenDate = CompalUtils.stringToDate(dateInput);
        String dayOfWeek = new SimpleDateFormat("EE").format(givenDate);


        String header = "\n" + "_".repeat(65) + "\n"
            + " ".repeat((92)) + dayOfWeek + "," + dateInput + "\n";
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

        if ("-".equals(startTime)) {
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
        if (t.getDescription().matches("(?i:.*lec.*)")) {
            taskSymbol = "Lect";
        } else if (t.getDescription().matches("(?i:.*tut.*)")) {
            taskSymbol = "Tut";
        } else if (t.getDescription().matches("(?i:.*sect.*)")) {
            taskSymbol = "Sect";
        } else if (t.getDescription().matches("(?i:.*lab.*)")) {
            taskSymbol = "Lab";
        } else if (t.getDescription().matches("(?i:.*rt.*)")) {
            taskSymbol = "RT";
        }

        String taskDescription = t.getDescription();
        taskDetails.append("  [").append(taskSymbol).append("] ")
            .append("[").append(status).append("] ")
            .append(taskDescription).append("\n\n");

        return taskDetails.toString();
    }
}