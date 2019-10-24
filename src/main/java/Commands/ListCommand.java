package Commands;
import Tasks.*;
import Interface.*;
/**
 * Represents the command to show the list of tasks on a TaskList object
 */
public class ListCommand extends Command {

    private final String list;
    private TaskList listToView;

    public ListCommand(String list){
        this.list = list;
    }
    /**
     * Executes the display of all the task in the TaskList object
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the list message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display list message
     */
    @Override
    public String execute(LookupTable LT,TaskList events, TaskList deadlines, Ui ui, Storage storage) {
        if(list.equals("event")) {
            listToView = events;
        } else if(list.equals("deadline")){
            listToView = deadlines;
        }
        return ui.showList(listToView);
    }
}
