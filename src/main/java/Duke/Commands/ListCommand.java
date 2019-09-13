package Duke.Commands;

import Duke.Constant.Duke_Response;
import Duke.Storage;
import Duke.Task.Task;
import Duke.Task.TaskList;
import Duke.Ui;

public class ListCommand extends Command{

    /**
     * Lists all the tasks stored in Duke. Sets message of Ui
     * to show all the tasks stored. Tasks are numbered starting from
     * index 1.
     * @param tasks The list of task stored by Duke
     * @param ui The user interface that handles messages
     * @param storage The database to read files and write txt files
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        String message;
        if(tasks.isEmpty()) {
            message = new Duke_Response().LIST_NOT_FOUND;
        } else{
            message = new Duke_Response().LIST_FOUND;
            int counter = 1;
            for (Task i : tasks) {
                message += counter + "." + i.toString();
                counter++;
            }
        }
        ui.setMessage(message);
    }
}
