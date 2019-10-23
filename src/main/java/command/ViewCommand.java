package command;

import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;
import java.util.ArrayList;

/**
 * Renders all the sorted tasks scheduled on a date.
 *
 * @author Sai Ganesh Suresh
 * @version v1.3
 */
public class ViewCommand extends Command {

    private String dateToFind;

    public ViewCommand(String dateToFind) {
        this.dateToFind = dateToFind;
    }

    /**
     * Finds all the tasks scheduled on a particular date and passes it to UI which prints to user.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     */
    public void execute(TaskList tasks, Storage storage) {
        ArrayList<Task> sortedRequiredSchedule = tasks.schedule(dateToFind);
        if (sortedRequiredSchedule.isEmpty()) {
            Ui.printMessage("There are no tasks scheduled on that date.");
        } else {
            Ui.printMessage("Here is your schedule for that day:");
            int i = 1;
            Ui.printDash();
            for (Task task : sortedRequiredSchedule) {
                Ui.printMessage(i++ + "." + task.toString());
            }
            Ui.printOutput("The tasks you have on this date are:");
            Integer j = 1;
            Integer k = 1;
            for (Task task : sortedRequiredSchedule) {
                Ui.printMessage(i++ + "." + task.toString());
                Ui.userOutputForUI += j++ + "." + task.toString() + "\n";
            }
        }
    }
}