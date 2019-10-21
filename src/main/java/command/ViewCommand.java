package command;

import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

import java.util.ArrayList;

/**
 * The ViewCommand class is used when the user intends to see all tasks
 * scheduled on a date!.
 *
 * @author Sai Ganesh Suresh
 * @version v1.0
 */
public class ViewCommand extends Command {

    private String dateToFind;

    public ViewCommand(String dateToFind) {
        this.dateToFind = dateToFind;
    }

    /**
     * This execute function is used to view the schedule on a particular date.
     *
     * @param tasks   this string holds command type determinant to decide how to
     *                process the user input.
     * @param storage this parameter provides the execute function the storage to
     *                allow the saving of the file.
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