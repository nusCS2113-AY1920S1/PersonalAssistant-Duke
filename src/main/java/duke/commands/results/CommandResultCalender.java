package duke.commands.results;

import duke.model.TaskList;

public class CommandResultCalender extends CommandResult {
    private TaskList tasks;

    /**
     * Constructs a basic CommandResultCalender object.
     *
     * @param message Message for ui to display.
     */
    public CommandResultCalender(String message) {
        this.message = message;
    }

    public void setTasks(TaskList tasks) {
        this.tasks = tasks;
    }

    public TaskList getTasks() {
        return tasks;
    }
}
