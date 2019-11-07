package duke.logic.command;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import duke.exception.DukeException;
import duke.logic.AbnormalityChecker;
import duke.extensions.Recurrence;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.task.Event;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

/**
 * duke.logic.parser.command.AddCommand that deals with the adding of new duke.task.Task objects to the duke.tasklist.TaskList
 */
public class AddCommand extends Command {
    private String description;
    private String taskType;

    private int duration;
    private Optional<String> filter;
    private Optional<LocalDateTime> dateTime;
    private Recurrence recurrence;
    private String priority;

    /**
     * Constructor for AddCommand
     * Creates a new task or event based on the inputs given
     *
     * @param filter filter for each task
     * @param dateTime datetime for each task
     * @param recurrence recurrence period for each task
     * @param description description for each task
     * @param taskType type of each task
     * @param duration time taken to complete each task
     * @throws DukeException if event has no starting time
     */
    public AddCommand(Optional<String> filter, Optional<LocalDateTime> dateTime, Optional<String> recurrence,
                      String description, String taskType, int duration, String priority) throws DukeException {
        this.filter = filter;
        this.dateTime = dateTime;
        this.recurrence = new Recurrence(recurrence);
        this.description = description;
        this.taskType = taskType;
        this.duration = duration;
        this.priority = priority;
    }

    /**
     * Executes the adding of a task to the TaskList
     *
     * @param tasks TaskList of all of user's tasks
     * @param ui Ui handling user interaction
     * @param storage Storage handling saving and loading of TaskList
     * @throws IOException
     * @throws DukeException
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, DukeException {
        switch (taskType) {
        case "event":
            if (!dateTime.isPresent()) {
                throw new DukeException("Your event needs to have a starting time.");
            }
            Event newEvent = new Event(filter, dateTime, recurrence, description, duration, priority);
            AbnormalityChecker abnormalityChecker = new AbnormalityChecker(tasks);
            if (abnormalityChecker.checkEventClash(newEvent)) {
                throw new DukeException("There is a clash with another event at the same time");
            } else {
                tasks.add(newEvent);
            }
            break;
        default:
            Task newTask = new Task(filter, dateTime, recurrence, description, duration, priority);
            tasks.add(newTask);
            break;
        }
        storage.save(tasks);
    }

    /**
     * Adds mirror command to savePrevState
     * Mirror command is a delete command at the same location as where the add was done.
     *
     * @param tasks TaskList of all of user's tasks
     * @param undoStack UndoStack of all mirror commands
     */
    @Override
    public void savePrevState(TaskList tasks, UndoStack undoStack) throws DukeException {
        int idx = tasks.size();
        undoStack.addAction(new DeleteCommand(Optional.empty(), Integer.toString(idx + 1)));
    }
}
