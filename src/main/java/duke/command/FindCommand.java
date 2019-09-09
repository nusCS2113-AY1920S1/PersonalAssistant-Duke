package duke.command;

import duke.commons.DukeException;
import duke.commons.Message;
import duke.commons.Ui;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;

/**
 * Represents a command that serach for tasks in TaskList.
 */
public class FindCommand extends Command {
    private String keyWord;

    public FindCommand(String keyWord) {
        this.keyWord = keyWord.strip();
    }

    public void execute(TaskList tasks, Storage storage) throws DukeException {
        TaskList results = new TaskList();
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (t.getDescription().contains(keyWord)) {
                results.add(t);
            }
        }
        Ui.showToUser(Message.getSearch(results));
    }
}
