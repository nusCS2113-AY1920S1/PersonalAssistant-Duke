package gazeeebo.commands.schedule;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;
import gazeeebo.tasks.Timebound;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.Stack;


/**
 * Lists out all the tasks that the user has in a specified week.
 */
public class ScheduleWeeklyCommand extends Command {
    //format for the command: scheduleWeekly <yyyy-MM-dd(Mon) yyyy-MM-dd(Sun)>
    protected LocalDate mon;
    protected LocalDate sun;
    private static final int ONE_WEEK = 7;

    /**
     * This is the main body of the ScheduleWeekly command.
     *
     * @param list the tasks list.
     * @param ui the object that deals with printing things to the user.
     * @param storage the object that deals with storing data to the Save.txt file.
     * @throws NullPointerException if tDate doesn't get updated.
     */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws NullPointerException {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            String[] date = ui.fullCommand.split(" ");
            if (date.length > 3) {
                System.out.println("The command should be in the format \"scheduleWeekly yyyy-MM-dd yyyy-MM-dd\".");
                return;
            }

            try {
                mon = LocalDate.parse(date[1], fmt);
            } catch (ArrayIndexOutOfBoundsException i) {
                System.out.println("OOPS!!! The description of a scheduleWeekly cannot be empty.");
                return;
            }

            try {
                sun = LocalDate.parse(date[2], fmt);
            } catch (ArrayIndexOutOfBoundsException a) {
                System.out.println("OOPS!!! Please specify an end date.");
                return;
            }

            long numOfDays = DAYS.between(mon, sun) + 1;
            if (mon.getDayOfWeek() != DayOfWeek.MONDAY) {
                System.out.println("The first date has to be a Monday.");
                return;
            } else if (sun.getDayOfWeek() != DayOfWeek.SUNDAY) {
                System.out.println("The second date has to be a Sunday.");
                return;
            } else if (mon.isAfter(sun)) {
                System.out.println("The second date has to be later than the first date.");
                return;
            } else if (numOfDays != ONE_WEEK) {
                System.out.println("The duration you have specified is longer than 1 week.");
                System.out.println(numOfDays);
                assert numOfDays > 7 : "numOfDays should be more than 7";
                return;
            }
        } catch (DateTimeParseException e) {
            System.out.println("Please input the date in yyyy-MM-dd format.");
            return;
        }

        ArrayList<Task> schedule = new ArrayList<Task>();
        for (Task t: list) {
            LocalDate tDate = null;
            if (t.getClass().getName().equals("gazeeebo.tasks.Event")) {
                tDate = ((Event) t).date;
            } else if (t.getClass().getName().equals("gazeeebo.tasks.Deadline")) {
                tDate = ((Deadline) t).by.toLocalDate();
            } else if (t.getClass().getName().equals("gazeeebo.tasks.Timebound")) {
                LocalDate startDate = ((Timebound) t).dateStart;
                LocalDate endDate = ((Timebound) t).dateEnd;
                if (endDate.equals(mon) || (startDate.isBefore(mon) && endDate.isAfter(mon)) ||
                        startDate.equals(mon) || (startDate.isAfter(mon) && startDate.isBefore(sun)) ||
                        startDate.equals(sun)) {
                    schedule.add(t);
                }
            }
            if (tDate != null && (tDate.equals(mon) || (tDate.isAfter(mon) &&
                    tDate.isBefore(sun)) || tDate.equals(sun))) {
                schedule.add(t);
            }
        }
        if (schedule.isEmpty()) {
            System.out.println("You have nothing scheduled for this week!");
        } else {
            System.out.println("Here is your schedule for the week:");
            for (int i = 0; i < schedule.size(); i++) {
                System.out.println((i+1) + "." + schedule.get(i).listFormat());
            }
        }
    }

    /**
     * Tells the main Duke class that the system should not exit and continue running
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }

}


