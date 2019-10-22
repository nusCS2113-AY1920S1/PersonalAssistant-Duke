package gazeeebo.commands.schedule;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;
import gazeeebo.tasks.Timebound;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Lists out all the tasks that the user has in a specified month.
 */
public class ScheduleMonthlyCommand extends Command {
    //format for the command: scheduleMonthly <yyyy-MM>
    protected LocalDate startMonth;
    protected LocalDate endMonth;

    /**
     * This is the main body of the ScheduleMonthly command.
     *
     * @param list the tasks list.
     * @param ui the object that deals with printing things to the user.
     * @param storage the object that deals with storing data to the Save.txt file.
     * @throws NullPointerException if tDate doesn't get updated.
     */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws NullPointerException {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String[] command = ui.fullCommand.trim().split(" ");
        if (command.length > 2) {
            System.out.println("The command should be in the format \"scheduleMonthly yyyy-MM\".");
            return;
        }
        try {
            startMonth = LocalDate.parse(command[1]+"-01", fmt);
            String lengthOfMonth = Integer.toString(startMonth.lengthOfMonth());
            endMonth = LocalDate.parse(command[1] + "-" + lengthOfMonth, fmt);
        } catch (DateTimeParseException e) {
            System.out.println("Please input the date in yyyy-MM format.");
            return;
        } catch (IndexOutOfBoundsException i) {
            System.out.println("OOPS!!! The description of a scheduleMonthly cannot be empty.");
            return;
        }
        ArrayList<Task> schedule = new ArrayList<Task>();
        for (Task t: list) {
            LocalDate tDate = null;
            switch (t.getClass().getName()) {
            case "gazeeebo.tasks.Event":
                tDate = ((Event) t).date;
                break;
            case "gazeeebo.tasks.Deadline":
                tDate = ((Deadline) t).by.toLocalDate();
                break;
            case "gazeeebo.tasks.Timebound":
                LocalDate startDate = ((Timebound) t).dateStart;
                LocalDate endDate = ((Timebound) t).dateEnd;
                if (!(endDate.isBefore(startMonth) || startDate.isAfter(endMonth))) {
                    schedule.add(t);
                }
                break;
            }
            if (tDate != null && startMonth.getYear() == tDate.getYear() &&
                    startMonth.getMonthValue() == tDate.getMonthValue()) {
                schedule.add(t);
            }
        }
        if (schedule.isEmpty()) {
            System.out.println("You have nothing scheduled for this month!");
        } else {
            System.out.println("Here is your schedule for " + command[1] + ":");
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
