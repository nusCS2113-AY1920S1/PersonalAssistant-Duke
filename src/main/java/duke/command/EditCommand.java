package duke.command;

import duke.exception.DukeException;
import duke.extensions.KeywordAndEdit;
import duke.parser.DateTimeParser;
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
    ArrayList<KeywordAndEdit> keywordAndEdits;

    public EditCommand(Optional<String> filter, int taskListIndex, ArrayList<KeywordAndEdit> keywordAndEdits) {
        this.filter = filter;
        this.taskListIndex = taskListIndex;
        this.keywordAndEdits = keywordAndEdits;
    }


    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, DukeException {
        for (int i = 0; i < keywordAndEdits.size(); i++) {
            String keyword = keywordAndEdits.get(i).getKeyword();
            String edit = keywordAndEdits.get(i).getEdit();
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
                default:
                    throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what field you are trying to edit!");
            }
        }
        storage.save(tasks);
    }
}
