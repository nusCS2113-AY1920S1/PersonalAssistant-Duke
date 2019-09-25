package duchess.logic.commands;

import duchess.storage.Storage;
import duchess.model.task.Task;
import duchess.model.task.TaskList;
import duchess.logic.commands.exceptions.DukeException;
import duchess.ui.Ui;

import java.util.List;
import java.util.stream.Collectors;

public class FindCommand extends Command {
    private List<String> words;

    public FindCommand(List<String> words) {
        this.words = words;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (words.size() > 0) {
            String searchTerm = String.join(" ", words.subList(0, words.size()));
            List<Task> filteredTasks =
                    taskList.getTasks().stream()
                            .filter(task -> task.containsKeyword(searchTerm))
                            .collect(Collectors.toList());
            if (filteredTasks.size() > 0) {
                ui.showSearchResult(filteredTasks);
            } else {
                throw new DukeException("There are no matching tasks.");
            }
        } else {
            throw new DukeException("Please enter at least a keyword to search.");
        }
    }
}
