package dolla.command;

/**
 * AddToDoCommand is a command used to add a todo task into the duke.task.TaskList.
 */
public abstract class AddTodoCommand extends AddCommand {

    public AddTodoCommand(String taskDescription) {
        super(taskDescription);
    }

    /**
     * Adds a todo task into the specified duke.task.TaskList.
     * <p>
     *     If the task description is missing, return without doing anything.
     * </p>
     */
    //@Override
    public void execute() {
        /*
        if (taskDescription.length() == 0) { // TODO: Exception handling?
            Ui.printMsg(missingDescriptionString);
            return;
        }

        Task newTask = new ToDo(taskDescription);
        tasks.add(newTask);
        Ui.echoAdd(newTask, tasks.size());

         */
    }
}
