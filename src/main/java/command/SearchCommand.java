package command;

import dictionary.WordBank;
import dictionary.WordCount;
import exception.NoWordFoundException;
import storage.Storage;
import ui.Ui;

/**
 * Represents a command from user to find tasks containing keywords specified.
 * Inherits from Command class.
 */
public class SearchCommand extends Command {

    protected String searchTerm;

    public SearchCommand(String queryWord) {
        this.searchTerm = queryWord;
    }

    @Override
    public String execute(Ui ui, WordBank wordBank, Storage storage, WordCount wordCount) {
        try {
            String meaning = wordBank.searchForMeaning(this.searchTerm);
            wordCount.increaseSearchCount(searchTerm, wordBank);
            return ui.showSearch(this.searchTerm, meaning);
        } catch (NoWordFoundException e) {
            return e.showError();
        }
    }
}
