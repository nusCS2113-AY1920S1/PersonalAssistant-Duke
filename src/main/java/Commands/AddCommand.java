package Commands;
import Tasks.*;
import Interface.*;
/**
 * Represents the command to add a Task object to a TaskList object.
 */
public class AddCommand extends Command {

    private Task task;

    /**
     * Creates an AddCommand object.
     * @param task The Task object to be added
     */
    public AddCommand(Task task){
        this.task = task;
    }

    /**
     * Executes the adding of a Task object to a TaskList object
     * and displaying the add task response.
     * @param list The TaskList object to add the task to
     * @param ui The Ui object to display the add task message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display add task message
     */
    @Override
    public String execute(TaskList list, Ui ui, Storage storage) {
        String out = "";
        if (task.getType().equals("[E]")) {
            int size = list.taskListSize();
            boolean[] conflict = new boolean[size];
            int con = 0;

            for (int i = 0; i < size; i++) {
                if (list.getTask(i).getDateTime().equals(task.getDateTime())) {
                    conflict[i] = true;
                    con++;
                }
            }
            if (con == 0) {
                list.addTask(this.task);
                out = ui.showAdd(this.task, list.taskListSize());

            } else {
                out = "Sorry, you have similar events at the same time and on the same day \n";
                for (int i = 0; i < size; i++) {
                    if (conflict[i]) {
                        out += list.getTask(i).toString() + "\n";
                    }
                }
            }
        } else {
            list.addTask(this.task);
            out = ui.showAdd(this.task, list.taskListSize());
        }
        return  out;
    }
}
