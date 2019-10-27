package duke.command;

import com.sun.source.tree.UsesTree;
import duke.exception.DukeException;
import duke.parser.DateTimeParser;
import duke.parser.KeywordAndField;
import duke.storage.Storage;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class EditCommand extends Command {
    Optional<String> filter;
    int taskListIndex;
    ArrayList<KeywordAndField> keywordAndFields;

    public EditCommand(Optional<String> filter, int taskListIndex, ArrayList<KeywordAndField> keywordAndFields) {
        this.filter = filter;
        this.taskListIndex = taskListIndex;
        this.keywordAndFields = keywordAndFields;
    }


    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, DukeException {
        for (int i = 0; i < keywordAndFields.size(); i++) {
            String keyword = keywordAndFields.get(i).getKeyword();
            String edit = keywordAndFields.get(i).getField();
            Task t = tasks.get(filter, taskListIndex);
            switch (keyword) {
                case "description":
                    t.setDescription(edit);
                    break;
                case "priority":
                    try {
                        int priorityLevel = Integer.parseInt(edit);
                        t.setPriority(priorityLevel);
                    } catch (NumberFormatException e) {
                        throw new DukeException("Please enter a numerical field for the duration!");
                    }
                    break;
                case "t":
                    Optional<LocalDateTime> dateTime = Optional.of(DateTimeParser.parseDateTime(edit));
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
                default:
                    throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what field you are trying to edit!");
            }
        }
        storage.save(tasks);
    }
}
