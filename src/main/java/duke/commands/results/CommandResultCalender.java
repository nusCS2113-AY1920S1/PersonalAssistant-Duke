package duke.commands.results;

import duke.model.TaskList;

public class CommandResultCalender extends CommandResult implements Calenderable, Taskable {
    private String message;
    private TaskList tasks;

    /**
     * Constructs a basic CommandResultCalender object.
     *
     * @param message Message for ui to display.
     */
    public CommandResultCalender(String message) {
        this.message = message;
    }

    @Override
    public void setTasks(TaskList tasks) {
        this.tasks = tasks;
    }

    @Override
    public TaskList getTasks() {
        return tasks;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
