package duke.commands;

import duke.constant.DukeResponse;
import duke.DateFormatter;
import duke.exception.DukeInvalidDateException;
import duke.Storage;
import duke.task.Deadline;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.Todo;
import duke.task.Event;
import duke.Ui;


public class AddCommand extends Command {
    private String taskType;
    private String description;
    private String dateTime;


    /**
     * Creates an AddCommand with the specified taskType and
     * description. Used for Todo task.
     * @param taskType The task type to be added
     * @param description The description of task
     */
    public AddCommand(String taskType, String description) {
        this.taskType = taskType;
        this.description = description;
        this.dateTime = "";
    }

    /**
     * Creates an AddCommand with the specified taskType,
     * description and dateTime. Used for Deadline and Event tasks
     * @param taskType The task type to be added
     * @param description The description of task
     * @param dateTime The date and time of task to either be completed or attend to
     */
    public AddCommand(String taskType, String description, String dateTime) {
        this.taskType = taskType;
        this.description = description;
        this.dateTime = dateTime;
    }

    /**
     * Append a new task to the TaskList.
     * @param tasks The list of task stored by duke
     * @param ui The user interface that handles messages
     * @param storage The database to read files and write txt files
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            Task task;
            DateFormatter dateFormatter = new DateFormatter(dateTime);
            switch (taskType.toLowerCase()) {
                case "todo":
                    task = new Todo(description);
                    tasks.add(task);
                    setResponse(ui, task.toString(), tasks.size());
                    break;
                case "event":
                    checkDateValidity(dateFormatter);
                    task = new Event(description, dateTime);
                    tasks.add(task);
                    setResponse(ui, task.toString(), tasks.size());
                    break;
                case "deadline":
                    checkDateValidity(dateFormatter);
                    task = new Deadline(description, dateTime);
                    tasks.add(task);
                    setResponse(ui, task.toString(), tasks.size());
                    break;
                default:
                    ui.setMessage("Invalid command");
            }
        } catch (DukeInvalidDateException e) {
            ui.setMessage(e.getMessage());
        }
    }

    /**
     * Check if input date is valid.
     * @param dateFormatter Checks validity of the date and time format
     * @throws DukeInvalidDateException If the date or time format is invalid
     */
    private void checkDateValidity(DateFormatter dateFormatter) throws DukeInvalidDateException {
        if (!dateFormatter.isValidDateTime()) {
            throw new DukeInvalidDateException();
        }
    }

    /**
     * Sets message of Ui based on the task that has been added.
     * @param ui The user interface that handles messages
     * @param taskString Details of the task added
     * @param size Number of existing task in ArrayList
     */
    private void setResponse(Ui ui, String taskString, int size) {
        ui.setMessage(new DukeResponse().ADD + taskString + "\n"
                + "Now you have " + size + " tasks in your list.\n");
    }
}
