package Duke.Commands;

import Duke.Constant.Duke_Response;
import Duke.Storage;
import Duke.Task.Task;
import Duke.Task.TaskList;
import Duke.Ui;

public class FindCommand extends Command{
    private String item;

    /**
     * Creates FindCommand with the specified item to be queried.
     * @param item String input specified by user to be queried.
     */
    public FindCommand(String item){
        this.item = item;
    }

    /**
     * Finds all the tasks that contains the item String. Sets message
     * of Ui to show if query was successful.
     * @param tasks The list of task stored by Duke
     * @param ui The user interface that handles messages
     * @param storage The database to read files and write txt files
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        TaskList foundTask = tasks.findTask(item);
        String message;

        if(foundTask.isEmpty()){
            message = new Duke_Response().NOT_FOUND;
        } else {
            message = new Duke_Response().FIND_FOUND;
            int counter = 1;
            for(Task i : foundTask){
                message += counter + "." + i.toString();
                counter++;
            }
        }
        ui.setMessage(message);
    }
}
