package duke.command;

import duke.commons.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;


/**
 * Represents a duke.command that search for tasks in TaskList.
 */
public class FindCommand extends Command {
    private String keyWord;

    public FindCommand(String keyWord) {
        this.keyWord = keyWord.strip();
    }

    public void execute(TaskList tasks, Storage storage, Ui ui) throws DukeException {
        TaskList results = new TaskList();
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (t.getDescription().contains(keyWord)) {
                results.add(t);
            }
        }
        ui.showMessage("Found " + results.size() + " duke.task(s).");
        ui.refreshTaskList(results, tasks);
    }
}
