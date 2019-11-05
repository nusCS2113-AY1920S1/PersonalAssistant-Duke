package Commands;

import Commons.LookupTable;
import Commons.Storage;
import Commons.Ui;
import UserInterface.AlertBox;
import Tasks.Assignment;
import Tasks.TaskList;
import javafx.scene.control.Alert;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents the command to add a Task object to a TaskList object.
 */
public class AddCommand extends Command {

    private final Assignment task;

    /**
     * Creates an AddCommand object.
     *
     * @param task The Task object to be added
     */
    public AddCommand(Assignment task) {
        this.task = task;
    }

    /**
     * Executes the adding of a Task object to a TaskList object
     * and displaying the add task response.
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the add task message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display add task message
     */
    @Override
    public String execute(LookupTable LT, TaskList events, TaskList deadlines, Ui ui, Storage storage) {
        String out = "";
        AlertBox alertBox = new AlertBox();
        ArrayList<String> conflict = new ArrayList<>();

        if (task.getType().equals("[E]")) {
            HashMap<String, HashMap<String, ArrayList<Assignment>>> eventsMap = events.getMap();
            if(eventsMap.containsKey(task.getModCode()) && eventsMap.get(task.getModCode()).containsKey(task.getDate())) {
                ArrayList<Assignment> temp = eventsMap.get(task.getModCode()).get(task.getDate());
                for (Assignment task : temp) {
                    {
                        if (task.getTime().equals(this.task.getTime())) {
                            conflict.add(task.toString());
                        }
                    }
                }
            }
            int size = events.taskListSize() + 1;

            if (conflict.size() == 0) {
                events.addTask(this.task);
                out = ui.showAdd(this.task,size);
                storage.updateEventList(events);
            }else{
                out = "Sorry, you have similar events at the same time and on the same day \n";
                String show = "";
                for (int i = 0; i< conflict.size();i++){
                    show += conflict.get(0);
                }
                alertBox.display("Warning", out, show, Alert.AlertType.WARNING);
            }
        } else if (task.getType().equals("[D]")) {
            deadlines.addTask(this.task);
            out = ui.showAdd(this.task, deadlines.taskListSize());
            storage.updateDeadlineList(deadlines);

        }
        return out;
    }
}