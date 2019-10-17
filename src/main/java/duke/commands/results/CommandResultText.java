package duke.commands.results;

import duke.model.TaskList;
import duke.model.events.Task;
import duke.model.locations.BusStop;

import java.util.ArrayList;

public class CommandResultText extends CommandResult {
    private TaskList tasks;

    /**
     * Constructs a basic CommandResultImage object.
     *
     * @param message Message for ui to display.
     */
    public CommandResultText(String message) {
        this.message = message;
    }

    /**
     * Alternative constructor that helps to create text for a list of tasks.
     */
    public CommandResultText(TaskList tasks) {
        message = "Here are the list of tasks:\n";
        int i = 1;
        for (Task t : tasks) {
            message += (i + ". " + t + "\n");
            i += 1;
        }
    }


    public void setTasks(TaskList tasks) {
        this.tasks = tasks;
    }

}
