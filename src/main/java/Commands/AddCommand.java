package Commands;

import Commons.DukeConstants;
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
    private static final String NUMBERING_DELIMITER = ". ";

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
    public String execute(TaskList events, TaskList deadlines, UserInteraction ui, Storage storage)
            throws DukeInvalidFormatException {
        String out = DukeConstants.NO_FIELD;

        ArrayList<String> eventConflict;
        ArrayList<String> deadlineConflict;
        if (task.getType().equals(DukeConstants.EVENT_INDICATOR)) {
            try {
                eventConflict = checkEventConflict(events, this.task);
            } catch (ParseException e) {
                LOGGER.severe("Invalid format for adding event");
                throw new DukeInvalidFormatException(DukeConstants.SAD_FACE + DukeConstants.EVENT_FORMAT);
            }
            int size = events.taskListSize() + 1;

            if (eventConflict.isEmpty()) {
                events.addTask(this.task);
                out = ui.showAdd(this.task,size);
                storage.updateEventList(events);
            } else {
                out = DukeConstants.CONFLICTING_EVENT;
                for (int i = 0; i < eventConflict.size(); i++) {
                    out += (i + 1) + NUMBERING_DELIMITER + eventConflict.get(i) + "\n";
                }
            }
        } else if (task.getType().equals(DukeConstants.DEADLINE_INDICATOR)) {
            deadlineConflict = checkDeadlineConflict(deadlines, this.task);
            int size = deadlines.taskListSize() + 1;
            if (deadlineConflict.isEmpty()) {
                deadlines.addTask(this.task);
                out = ui.showAdd(this.task,size);
                storage.updateDeadlineList(deadlines);
            } else {
                out = DukeConstants.CONFLICTING_DEADLINE;
                for (int i = 0; i < deadlineConflict.size();i++) {
                    out += (i + 1) + NUMBERING_DELIMITER + deadlineConflict.get(i) + "\n";
                }
            }
        }
        return out;
    }
}