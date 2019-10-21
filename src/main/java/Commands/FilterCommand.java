package Commands;

import Interface.Storage;
import Interface.Ui;
import Tasks.Task;
import Tasks.TaskList;

import java.util.ArrayList;

/**
 * Represents the command to show the list of tasks corresponding to a keyword
 */
public class FilterCommand extends  Command{
    private String keyword;

    public FilterCommand(String keyword){
        this.keyword =keyword;
    }
    /**
     * Execute the displaying of all task the contains a certain keyword
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the list message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display list message
     */

    @Override
    public String execute(TaskList events, TaskList deadlines, Ui ui, Storage storage) throws Exception {
        TaskList list = new TaskList();
        ArrayList<Task> event = events.getList();
        ArrayList<Task> deadline = deadlines.getList();

        for(int i = 0; i <event.size();i++){
            if (event.get(i).toString().toLowerCase().contains(keyword)|
                event.get(i).toString().toUpperCase().contains(keyword) ){
                list.addTask(event.get(i));
            }
        }
        for(int i = 0; i <deadline.size();i++){
            if (deadline.get(i).toString().toLowerCase().contains(keyword)|
                    deadline.get(i).toString().toUpperCase().contains(keyword)){
                list.addTask(deadline.get(i));
            }
        }

        return ui.showFilter(list,this.keyword);
    }
}
