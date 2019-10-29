package duke.command;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import duke.exception.DukeException;
import duke.extensions.AbnormalityChecker;
import duke.extensions.Recurrence;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.task.Event;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

/**
 * duke.command.AddCommand that deals with the adding of new duke.task.Task objects to the duke.tasklist.TaskList
 */
public class AddCommand extends Command {
    private String description;
    private String taskType;

    private int duration;
    private Optional<String> filter;
    private Optional<LocalDateTime> dateTime;
    private Recurrence recurrence;

    public AddCommand(Optional<String> filter, Optional<LocalDateTime> dateTime, Optional<String> recurrence,
                      String description, String taskType, int duration) throws DukeException {
        this.filter = filter;
        this.dateTime = dateTime;
        this.recurrence = new Recurrence(recurrence);
        this.description = description;
        this.taskType = taskType;
        this.duration = duration;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, DukeException {
        switch (taskType) {
        case "event":
            if (!dateTime.isPresent()) {
                throw new DukeException("Your event needs to have a starting time.");
            }
            Event newEvent = new Event(filter, dateTime, recurrence, description, duration);
            AbnormalityChecker abnormalityChecker = new AbnormalityChecker(tasks);
            if (abnormalityChecker.checkEventClash(newEvent)) {
                System.out.println("There is a clash with another event at the same time");
            } else {
                tasks.add(newEvent);
            }
            break;
        default:
            Task newTask = new Task(filter, dateTime, recurrence, description, duration);
            tasks.add(newTask);
            break;
        }
        storage.save(tasks);
    }

    @Override
    public void savePrevState(TaskList tasks, UndoStack undoStack) {
        int idx = tasks.size();
        undoStack.addAction(new DeleteCommand(Optional.empty(), Integer.toString(idx + 1)));
    }
}
