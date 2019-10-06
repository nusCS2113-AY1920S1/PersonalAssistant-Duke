package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.task.Task;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.List;
import java.util.stream.Collectors;

public class FindCommand extends Command {
    private List<String> words;

    public FindCommand(List<String> words) {
        this.words = words;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        if (words.size() > 0) {
            String searchTerm = String.join(" ", words.subList(0, words.size()));
            List<Task> filteredTasks =
                    store.getTaskList().stream()
                            .filter(task -> task.containsKeyword(searchTerm))
                            .collect(Collectors.toList());
            if (filteredTasks.size() > 0) {
                ui.showSearchResult(filteredTasks);
            } else {
                throw new DuchessException("There are no matching tasks.");
            }
        } else {
            throw new DuchessException("Please enter at least a keyword to search.");
        }
    }
}
