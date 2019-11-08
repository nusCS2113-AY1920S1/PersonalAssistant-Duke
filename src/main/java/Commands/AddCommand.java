package Commands;

import Commons.DukeLogger;
import Commons.Storage;
import Commons.UserInteraction;
import DukeExceptions.DukeInvalidFormatException;
import Tasks.Assignment;
import Tasks.TaskList;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Represents the command to add a Task object to a TaskList object.
 */
public class AddCommand extends Command {

    private final Assignment task;
    private final Logger LOGGER = DukeLogger.getLogger(AddCommand.class);

    /**
     * Creates an AddCommand object.
     *
     * @param task The Task object to be added
     */
    public AddCommand(Assignment task) {
        this.task = task;
    }

    /**
     * Executes the adding of a Task object to a TaskList object.
     * and displaying the add task response.
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the add task message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display add task message
     */
    @Override
    public String execute(TaskList events, TaskList deadlines, UserInteraction ui, Storage storage) throws ParseException, DukeInvalidFormatException {
        String out = "";

        ArrayList<String> eventConflict;
        ArrayList<String> deadlineConflict;
        if (task.getType().equals("[E]")) {
            try {
                eventConflict = checkEventConflict(events, this.task);
            } catch (ParseException e) {
                LOGGER.severe("Invalid format for adding event");
                throw new DukeInvalidFormatException("OOPS!!! Please enter event as follows:\n"
                        + "add/e modCode name_of_event /at dd/MM/yyyy /from HHmm /to HHmm\n"
                        + "or add/e modCode name_of_event /at week x day /from HHmm /to HHmm\n "
                        + "For example: add/e CS1231 project meeting /at 1/1/2020 /from 1500 /to 1700");
            }
            int size = events.taskListSize() + 1;

            if (eventConflict.size() == 0) {
                events.addTask(this.task);
                out = ui.showAdd(this.task,size);
                storage.updateEventList(events);
            } else {
                out = "Sorry, you have conflicting events \n";
                for (int i = 0; i < eventConflict.size(); i++) {
                    out += (i + 1) + ". " + eventConflict.get(i) + "\n";
                }
            }
        } else if (task.getType().equals("[D]")) {
            deadlineConflict = checkDeadlineConflict(deadlines, this.task);
            int size = deadlines.taskListSize() + 1;
            if (deadlineConflict.size() == 0) {
                deadlines.addTask(this.task);
                out = ui.showAdd(this.task,size);
                storage.updateDeadlineList(deadlines);
            } else {
                out = "Sorry, you have conflicting deadlines \n";
                for (int i = 0; i < deadlineConflict.size();i++) {
                    out += (i + 1) + ". " + deadlineConflict.get(i) + "\n";
                }
            }
        }
        return out;
    }


}