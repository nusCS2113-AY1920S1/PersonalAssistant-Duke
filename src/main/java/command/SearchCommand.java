package command;

import Dictionary.Word;
import Dictionary.WordBank;
import exception.NoWordFoundException;
import storage.Storage;
import ui.Ui;

/**
 * Represents a command from user to find tasks containing keywords specified.
 * Inherits from Command class.
 */
public class SearchCommand extends Command {

    protected String searchedWord;

    public SearchCommand(String queryWord) {
        this.searchedWord = queryWord;
    }

    @Override
    public void execute(Ui ui, WordBank wordBank, Storage storage){
        //ask ui to print something
        //ask tasks to store the thing in arraylist
        //ask storage to write to file
        /*try {
            String meaning = wordBank.searchForMeaning(this.searchedWord);
            ui.showSearch(this.searchedWord, meaning);
        }
        catch (NoWordFoundException e) {
            e.showError();
        }*/
        String meaning = wordBank.searchForMeaning(this.searchedWord);
        ui.showSearch(this.searchedWord,meaning);
    }
}
