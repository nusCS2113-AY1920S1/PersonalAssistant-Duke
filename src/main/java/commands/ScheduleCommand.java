package commands;
import Storage.Storage;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import UI.Ui;
import Exception.DukeException;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Lists out all the tasks the user has on the specified day.
 */
public class ScheduleCommand extends Command {
    //format for the command: schedule <yyyy-MM-dd>
    protected LocalDate date;
    /**
     * This is the main body of the schedule command.
     *
     * @param list the tasks list.
     * @param ui the object that deals with printing things to the user.
     * @param storage the object that deals with storing data to the Save.txt file.
     * @param commandStack
     * @param deletedTask
     * @throws NullPointerException if tDate doesn't get updated.
     */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask) throws DukeException, ParseException, IOException, NullPointerException {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            date = LocalDate.parse(ui.FullCommand.trim().split(" ")[1], fmt);
        } catch (DateTimeParseException e) {
            System.out.println("Please input the date in yyyy-MM-dd format.");
            return;
        } catch (IndexOutOfBoundsException i) {
            System.out.println("OOPS!!! The description of a schedule cannot be empty.");
            return;
        }
        ArrayList<Task> schedule = new ArrayList<Task>();
        for (Task t: list) {
            LocalDate tDate = null;
            if (t.getClass().getName().equals("Tasks.Event")) {
                tDate = ((Event) t).date;
            } else if (t.getClass().getName().equals("Tasks.Deadline")) {
                tDate = ((Deadline) t).by.toLocalDate();
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


