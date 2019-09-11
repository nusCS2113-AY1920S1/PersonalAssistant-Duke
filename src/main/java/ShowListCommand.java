import java.util.ArrayList;

/**
 * ShowListCommand is a command used to display all the tasks stored in the TaskList.
 */
public class ShowListCommand extends Command {

    /**
     * Creates and prints out the tasks from the specified TaskList in a readable format.
     * @param tasks TaskList to have its tasks printed.
     */
    @Override
    public void execute(TaskList tasks) {

            ArrayList<String> msg = new ArrayList<String>();
            msg.add("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                Task currTask = tasks.getFromList(i);
                msg.add( (i+1) + "."  + currTask.getTask() );
            }
            Ui.printMsg(msg);

    }
}
