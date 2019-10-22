package duke.logic.commands.results;

import duke.model.lists.TaskList;
import duke.model.events.Task;

public class CommandResultText extends CommandResult {
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
}
