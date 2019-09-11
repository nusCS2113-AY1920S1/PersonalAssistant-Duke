import java.util.ArrayList;

/**
 * CompleteCommand is a type of command used to change the variable 'isDone'
 * in given particular task in the task list to true.
 */
public class CompleteCommand extends Command {
    protected String taskNumStr;

    /**
     * Creates an instance of CompleteCommand with the specified task number.
     * @param taskNumStr The number of the task in task list to be marked as done.
     */
    public CompleteCommand(String taskNumStr) {
        this.taskNumStr = taskNumStr;
    }

    /**
     * If the 'isDone' variable of the task in 'tasks' corresponding to 'taskNumStr' is false,
     * change to true and print out confirmation message.
     * <p>
     *     If 'taskNumStr' does not correspond to any of the task numbers in 'tasks', print an
     *     error message and return without changing 'tasks'.
     * </p>
     * <p>
     *     If the 'isDone' variable of the task is already true, print a message to alert the user.
     * </p>
     * @param tasks The task list to be accessed.
     * @throws Exception
     */
    @Override
    public void execute(TaskList tasks) throws Exception {
        int taskNumInt = stringToInt(taskNumStr);
        if (taskNumInt == 0) return; // don't do anything if task number is invalid

        ArrayList<String> msg = new ArrayList<String>();

        Task currTask;
        try {
            currTask = tasks.getFromList(taskNumInt-1);
        } catch (IndexOutOfBoundsException e) {
            msg.add(taskNumInt + " is not associated to any task number.");
            msg.add("Use 'list' to check the tasks that are here first!");
            Ui.printMsg(msg);
            throw new Exception("Task number not in list");
        }

        if (currTask.isDone == true) {
            msg.add("Task " + taskNumInt + " is already completed! :)");
        } else {
            currTask.markAsDone();
            msg.add("Nice! I've marked this task as done:");
            msg.add("  " + currTask.getTask());
        }
        Ui.printMsg(msg);

    }
}
