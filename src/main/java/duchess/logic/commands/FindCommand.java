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

    /**
     * Returns the length of the longest common subsequence of 2 Strings.
     *
     * @param a the first String
     * @param b the second String
     * @return the length of the longest common subsequence
     */
    private int longestCommonSubsequence(String a, String b) {
        int[][] table = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i < table.length - 1; i++) {
            for (int j = 0; j < table[0].length - 1; j++) {
                if (a.charAt(i) == b.charAt(j)) {
                    table[i + 1][j + 1] = table[i][j] + 1;
                } else {
                    table[i + 1][j + 1] = Math.max(table[i][j + 1], table[i + 1][j]);
                }
            }
        }
        return table[a.length()][b.length()];
    }

    public FindCommand(List<String> words) {
        this.words = words;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        if (words.size() == 0) {
            throw new DuchessException("Please enter at least a keyword to search.");
        } else {
            String searchTerm = String.join(" ", words.subList(0, words.size()));
            List<Task> filteredTasks;
            /*
            If search term is enclosed by double quotation marks,
            search for exact matches.
             */
            if (searchTerm.charAt(0) == '"' && searchTerm.charAt(searchTerm.length() - 1) == '"') {
                filteredTasks = store
                        .getTaskList()
                        .stream()
                        .filter(task -> task.getDescription()
                        .equals(searchTerm.substring(1, searchTerm.length() - 1)))
                        .collect(Collectors.toList());
            /*
            Search for task descriptions with longest common subsequence of length
            equal to at least 1 less than the length of the search term.
             */
            } else {
                filteredTasks = store
                        .getTaskList().stream()
                        .filter(task -> longestCommonSubsequence(task.getDescription()
                        .replaceAll(" ", ""), searchTerm.replaceAll(" ", ""))
                        >= searchTerm.replaceAll(" ", "").length() - 1)
                        .collect(Collectors.toList());
            }
            
            storage.setPreviousUndoFalse();
          
            if (filteredTasks.size() == 0) {
                throw new DuchessException("There are no matching tasks.");
            } else {
                ui.showSearchResult(filteredTasks);
            }
        }
    }
}
