package Commands;

import Commons.Storage;
import Commons.UserInteraction;
import Tasks.Assignment;
import Tasks.TaskList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Represents the command to show the list of tasks corresponding to a keyword
 */
public class FilterCommand extends Command{
    private String keyword;


    /**
     * Creates FilterCommand object.
     * @param Command The full command that calls FilterCommand.
     */
    public FilterCommand(String Command)
    {
        this.keyword = Command.trim().substring(7);
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
    public String execute(TaskList events, TaskList deadlines, UserInteraction ui, Storage storage) {
        ArrayList<String> out = new ArrayList<>();
        HashMap<String, HashMap<String, ArrayList<Assignment>>> emap = events.getMap();
        Set<String> allMods = emap.keySet();
        for (String mod : allMods) {
            Set<String> allDates = emap.get(mod).keySet();
            for (String date : allDates) {
                ArrayList<Assignment> temp = emap.get(mod).get(date);
                for(Assignment task : temp) {
                    if (task.toString().toLowerCase().contains(keyword)|
                            task.toString().toUpperCase().contains(keyword) ){
                        out.add(task.getDescription()+ " " +task.getModCode() + " " + task.getDateTime() );
                    }
                }
            }
        }
        HashMap<String, HashMap<String, ArrayList<Assignment>>> dmap = deadlines.getMap();
        Set<String> allMods1 = dmap.keySet();
        for (String mod : allMods1) {
            Set<String> allDates = dmap.get(mod).keySet();
            for (String date : allDates) {
                ArrayList<Assignment> temp = dmap.get(mod).get(date);
                for(Assignment task : temp) {
                    if (task.toString().toLowerCase().contains(keyword)|
                            task.toString().toUpperCase().contains(keyword) ){
                        out.add(task.getDescription()+ " " +task.getModCode() + " " + task.getDateTime() );
                    }
                }
            }
        }


        return ui.showFilter(out,this.keyword);
    }
}
