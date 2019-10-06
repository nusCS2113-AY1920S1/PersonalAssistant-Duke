package command;

import Dictionary.Word;
import Dictionary.WordBank;
import exception.NoWordFoundException;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

import java.util.ArrayList;

/**
 * Represents a command from user to find tasks containing keywords specified.
 * Inherits from Command class.
 * @author Zhang Yue Han
 */
public class FindCommand extends Command {

    String searchedWord;

    public FindCommand(String searchedWord) {
        this.searchedWord = searchedWord;
    }

    @Override
    public void execute(Ui ui, WordBank wordBank, Storage storage) {
        //ask ui to print something
        //ask tasks to store the thing in arraylist
        //ask storage to write to file
        try {
            word = wordBank.getWordAndMeaning(searchedWord);
            wordBank.searchForMeaning(searchedWord);
            System.out.println("     Here is the word that you are looking for: " + word);
        }
        catch (NoWordFoundException e) {
            e.showError();
        }
    }
}
