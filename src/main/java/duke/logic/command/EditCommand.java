package duke.logic.command;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import duke.exception.DukeException;
import duke.logic.AbnormalityChecker;
import duke.logic.parser.DateTimeParser;
import duke.logic.parser.KeywordAndField;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.task.Event;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

/**
 * Class that handles the editing of a given task and updating given values of the task
 */
public class EditCommand extends Command {
    private static List<String> priorityAllowed = Arrays.asList("high", "medium", "low", "h", "m", "l");

    private Optional<String> filter;
    private int taskListIndex;
    private ArrayList<KeywordAndField> keywordAndFields;

    /**
     * Constructor of EditCommand
     * Finds the actual location of the task in the user TaskList using a given filter and index
     * keyword and fields include all new fields to be updated
     *
     * @param filter           filter for each task
     * @param taskListIndex    index of the task
     * @param keywordAndFields keywords and corresponding fields to be updated
     */
    public EditCommand(Optional<String> filter, int taskListIndex, ArrayList<KeywordAndField> keywordAndFields) {
        this.filter = filter;
        this.taskListIndex = taskListIndex;
        this.keywordAndFields = keywordAndFields;
    }

    /**
     * Executes the editing process to change a task's attributes
     * Multiple attributes can be updated at once.
     *
     * @param tasks   TaskList of all of user's tasks
     * @param ui      Ui handling user interaction
     * @param storage Storage handling saving and loading of TaskList
     * @throws IOException   NA
     * @throws DukeException if invalid user inputs are given
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, DukeException {
        for (int i = 0; i < keywordAndFields.size(); i++) {
            String keyword = keywordAndFields.get(i).getKeyword();
            String edit = keywordAndFields.get(i).getField();
            Task t = tasks.get(filter, taskListIndex);
            switch (keyword) {
            case "des":
            case "desc":
            case "descript":
            case "description":
                t.setDescription(edit);
                break;
            case "p":
            case "priority":
                String p = edit.trim().toLowerCase();
                if (!priorityAllowed.contains(p)) {
                    throw new DukeException("Please enter a valid priority description!");
                }
                t.editPriority(p);
                break;
            case "t":
                Optional<LocalDateTime> dateTime = Optional.of(DateTimeParser.parseDateTime(edit));
                if (t instanceof Event) {
                    AbnormalityChecker abnormalityChecker = new AbnormalityChecker(tasks);
                    if (abnormalityChecker.checkEventClash((Event) t)) {
                        throw new DukeException("There is a clash with another event at the same time");
                    } else {
                        t.setDateTime(dateTime);
                    }
                }
                t.setDateTime(dateTime);
                break;
            case "d":
                try {
                    int duration = Integer.parseInt(edit);
                    t.setDuration(duration);
                } catch (NumberFormatException e) {
                    throw new DukeException("Please enter a numerical field for the duration!");
                }
                break;
            case "r":
                t.setRecurrence(edit);
                break;
            case "f":
                if (edit.split(" ").length != 1) {
                    throw new DukeException("Please enter only one word for the filter!");
                }
                t.setFilter(Optional.of(edit));
                break;
            default:
                throw new DukeException("I'm sorry, I do not know what field you are trying to edit!");
            }
        }
        storage.save(tasks);
    }

    /**
     * Adds a mirror command to top of the the UndoStack
     * Allows the user to undo this EditCommand call in the event of a mistake
     *
     * @param tasks     TaskList of all of user's tasks
     * @param undoStack UndoStack containing all mirror commands
     * @throws DukeException if invalid index is given
     */
    @Override
    public void savePrevState(TaskList tasks, UndoStack undoStack) throws DukeException {
        Task t = tasks.get(filter, taskListIndex);
        undoStack.addAction(new SetCommand(filter, taskListIndex, new Task(t)));
    }
}
