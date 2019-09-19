package duke.command;

import duke.task.DukeException;
import duke.task.Ui;
import duke.task.Storage;
import duke.task.TaskList;
import duke.task.Task;

public class ViewSchedule extends Command {

    /**
     * This class allows user to see the schedule for a specific date.
     * @param isExit is true if the command to exit the program is called for else it is false
     * @param input the command typed in by the user
     */
    public ViewSchedule(boolean isExit, String input) {
        super(isExit, input);
    }

    /**
     * This function will find the tasks to be done on a certain date specified by the user.
     * @param taskList Task List containing the initialized lists of the task on run
     * @param ui Ui for which any input and output will be given to
     * @param storage Storage for storing and writing of the data to disk
     * @throws DukeException thrown when there is invalid input or there is no task available
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (input.length() < 6) {
            throw new DukeException("OOPS!!! The date to search for cannot be empty.");
        }

        input = input.substring(5);

        if (taskList.getSize() == 0) {
            throw new DukeException("You have no tasks in your list");
        }

        boolean found = false;
        int start = 1;
        String schedule = "";
        schedule += "Here is your schedule for " + input + ":\n";
        int i = 0;
        while (i < taskList.getSize()) {
            Task task = taskList.getTask(i);
            if (task.toString().contains(input)) {
                schedule += start + "." + task.toString() + "\n";
                start++;
                found = true;
            }
            i++;
        }

        if (found) {
            schedule = schedule.substring(0, schedule.length() - 1);
            ui.output = schedule;
        } else {
            ui.output = "There is no task available on this date.";
        }
    }
}
