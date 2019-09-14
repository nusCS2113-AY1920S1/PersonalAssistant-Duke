package duke.command;

import duke.dukeexception.DukeException;
import duke.task.Snoozeable;
import duke.task.Task;
import duke.task.TaskList;

import java.util.List;

public class SnoozeCommand extends Command {
    private List<String> words;

    public SnoozeCommand(List<String> words) {
        this.words = words;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        try {
            int taskNo = Integer.parseInt(words.get(0)) - 1;
            Task task = taskList.get(taskNo);

            if (!(task instanceof Snoozeable)) {
                throw new DukeException("You can't snooze that task.");
            }

            ((Snoozeable) task).snooze();
            ui.showSnoozedTask((Snoozeable) task);
            storage.save(taskList);
        } catch (NumberFormatException e) {
            throw new DukeException("Please supply a number. Eg: done 2");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Please supply a valid number.");
        }
    }
}
