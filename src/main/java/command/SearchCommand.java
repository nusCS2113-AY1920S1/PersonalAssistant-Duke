package command;

import dictionary.WordBank;
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
    public String execute(Ui ui, WordBank wordBank, Storage storage) {
        try {
            String meaning = wordBank.searchForMeaning(this.searchTerm);
            wordBank.increaseSearchCount(searchTerm);
            return ui.showSearch(this.searchTerm, meaning);
        }
        catch (NoWordFoundException e) {
            return e.showError();
        }
    }
}
