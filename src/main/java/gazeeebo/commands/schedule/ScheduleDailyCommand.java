package gazeeebo.commands.schedule;

import gazeeebo.storage.Storage;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;
import gazeeebo.tasks.Timebound;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;
import gazeeebo.commands.Command;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Lists out all the tasks that the user has on the specified day.
 */
public class ScheduleDailyCommand extends Command {
    //format for the command: scheduleDaily <yyyy-MM-dd>
    protected LocalDate date;

    /**
     * This is the main body of the ScheduleDaily command.
     *
     * @param list the tasks list.
     * @param ui the object that deals with printing things to the user.
     * @param storage the object that deals with storing data to the Save.txt file.
     * @param commandStack not used
     * @param deletedTask not used
     * @throws NullPointerException if tDate doesn't get updated.
     */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String[] command = ui.fullCommand.split(" ");
        if (command.length > 2) {
            System.out.println("The command should be in the format \"scheduleDaily yyyy-MM-dd\".");
            return;
        }
        try {
            date = LocalDate.parse(command[1], fmt);
        } catch (DateTimeParseException e) {
            System.out.println("Please input the date in yyyy-MM-dd format.");
            return;
        } catch (IndexOutOfBoundsException i) {
            System.out.println("OOPS!!! The description of a scheduleDaily cannot be empty.");
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
                    if (date.equals(startDate) || date.equals(endDate) ||
                            (date.isAfter(startDate) && date.isBefore(endDate))) {
                        schedule.add(t);
                    }
                    break;
            }
            if (date.equals(tDate)) {
                schedule.add(t);
            }
        }
        if (schedule.isEmpty()) {
            System.out.println("You have nothing scheduled on this day!");
        } else {
            System.out.println("Here is your schedule for " + date.format(fmt) + ":");
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


