package dolla.command;

/**
 * duke.command.AddDeadlineCommand is a command used to add a deadline task into the duke.task.TaskList.
 */
public abstract class AddDeadlineCommand extends AddCommand {

    /**
     * Instantiates a new Add deadline command.
     *
     * @param taskDescription the task description
     */
    public AddDeadlineCommand(String taskDescription) {
        super(taskDescription); // taskDescription includes the task info and date
        time = null;
        dateTrigger = "/by";
    }

    /**
     * Adds a deadline task into the specified duke.task.TaskList.
     * <p>
     *     The method first splits the input data into the task description and time.
     *     Using the newly acquired data, a new duke.task.Deadline duke.task.Task is created and then stored into
     *     the specified duke.task.TaskList.
     * </p>
     * <p>
     *     If the method encounters an error when trying to split the input data, the method
     *     returns without doing anything.
     * </p>
     */
    //@Override
    public void execute() {
        /*
        if (!splitDescTime()) {
            return; // If error occurs, stop the method!
        }
        if (!detectAnomalies(tasks, time)) {
            return; // If error occurs, stop the method!
        }
        Task newTask = new Deadline(taskDescription, time);
        tasks.add(newTask);
        Ui.echoAdd(newTask, tasks.size());

         */
    }

}
