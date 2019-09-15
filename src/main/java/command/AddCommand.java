package command;
import process.*;

import process.DukeException;
import task.*;

/**
 * Represents a command that adds an item to tasks
 */
public class AddCommand extends Command {
    private String description;
    private String tasktype;
    private String datetime;
    private int durationHour, durationMinute;
    /**
     * Creates a new AddCommand object with the given type of task and description
     * @param tasktype The task type
     * @param description of the task
     */
    public AddCommand(String tasktype, String description) {
        this.description = description;
        this.tasktype = tasktype;
    }
    /**
     * Creates a new AddCommand object with the given type of task, description and date time
     * @param tasktype The task type
     * @param description of the task
     * @param datetime the date and time
     */
    public AddCommand(String tasktype, String description, String datetime) {
        this.description = description;
        this.tasktype = tasktype;
        this.datetime = datetime;
    }

    public AddCommand(String description, int durationHour, int durationMinute ){
        this.tasktype = "task";
        this.description = description;
        this.durationHour = durationHour;
        this.durationMinute = durationMinute;
    }
    /**
     * Executes the AddCommand and saves changes to storage
     * @param tasks the task list
     * @param storage the storage file
     * @param ui the user interface object
     * @return ui response as a string
     */
    public String execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (tasktype.equals("todo")) tasks.add(new Todo(description, false));
        else if (tasktype.equals("deadline")) tasks.add(new Deadline(description, datetime, false));
        else if (tasktype.equals("event")) tasks.add(new Event(description, datetime, false));
        else if (tasktype.equals("task")) tasks.add(new FixedTask(description, false, durationHour, durationMinute));
        else throw new DukeException("add error");
        storage.save(tasks);
        return ui.showTaskAdded(tasks.get(tasks.size()-1).toString(), tasks.size());
    }
}