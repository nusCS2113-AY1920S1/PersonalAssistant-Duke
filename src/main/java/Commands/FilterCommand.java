package Commands;

import Interface.LookupTable;
import Interface.Storage;
import Interface.Ui;
import Tasks.Task;
import Tasks.TaskList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

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
    public String execute(LookupTable LT, TaskList events, TaskList deadlines, Ui ui, Storage storage) throws Exception {
        ArrayList<String> out = new ArrayList<>();
        HashMap<String, HashMap<String, ArrayList<Task>>> emap = events.getMap();
        Set<String> allMods = emap.keySet();
        for (String mod : allMods) {
            Set<String> allDates = emap.get(mod).keySet();
            for (String date : allDates) {
                ArrayList<Task> temp = emap.get(mod).get(date);
                for(Task task : temp) {
                    if (task.toString().toLowerCase().contains(keyword)|
                            task.toString().toUpperCase().contains(keyword) ){
                        out.add(task.toString());
                    }
                }
            }
        }
        HashMap<String, HashMap<String, ArrayList<Task>>> dmap = deadlines.getMap();
        Set<String> allMods1 = dmap.keySet();
        for (String mod : allMods1) {
            Set<String> allDates = dmap.get(mod).keySet();
            for (String date : allDates) {
                ArrayList<Task> temp = dmap.get(mod).get(date);
                for(Task task : temp) {
                    if (task.toString().toLowerCase().contains(keyword)|
                            task.toString().toUpperCase().contains(keyword) ){
                        out.add(task.toString());
                    }
                }
            }
        }


        return ui.showFilter(out,this.keyword);
    }
}
