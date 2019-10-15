package Commands;
import JavaFx.AlertBox;
import Tasks.*;
import Interface.*;
import javafx.scene.control.Alert;

import java.io.FileNotFoundException;

/**
 * Represents the command to add a Task object to a TaskList object.
 */
public class AddCommand extends Command {

    private final Task task;

    /**
     * Creates an AddCommand object.
     *
     * @param task The Task object to be added
     */
    public AddCommand(Task task) {
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
    public String execute(TaskList events, TaskList deadlines, Ui ui, Storage storage) throws FileNotFoundException {
        String out = "";
        int con = 0;
        boolean isOK = true;
        AlertBox AB = new AlertBox();
        if (task.getType().equals("[E]")) {
            int size = events.taskListSize();
            boolean[] conflict = new boolean[size];
            for (int i = 0; i < size; i++) {
                if (events.getTask(i).getDateTime().equals(task.getDateTime())) {
                    conflict[i] = true;
                    con++;
                }
            }
            if (con == 0) {
                events.addTask(this.task);
                out = "true|" + ui.showAdd(this.task, events.taskListSize());
                storage.updateEventList(events);
            } else {
                out = "Sorry, you have similar events at the same time and on the same day \n";
                for (int i = 0; i < size; i++) {
                    if (conflict[i]) {
                        out += events.getTask(i).toString() + "\n";
                    }
                }
                AB.display("Warning", "Clash in events", out, Alert.AlertType.WARNING);

            }
        } else if (task.getType().equals("[D]")) {
            int size = deadlines.taskListSize();
            boolean[] conflict = new boolean[size];
            for (int i = 0; i < size; i++) {
                if (deadlines.getTask(i).getDateTime().equals(task.getDateTime())) {
                    conflict[i] = true;
                    con++;
                }
            }
            if (con != 0) {
                out = "";
                for (int i = 0; i < size; i++) {
                    if (conflict[i]) {
                        out += deadlines.getTask(i).toString() + "\n";
                    }
                }
                isOK = AB.display("Note", "Similar deadline", "Here are the list of similar deadlines", Alert.AlertType.INFORMATION);
            } else {
                deadlines.addTask(this.task);
                out = ui.showAdd(this.task, deadlines.taskListSize());
                storage.updateDeadlineList(deadlines);
            }
        }
        return out;
    }
}