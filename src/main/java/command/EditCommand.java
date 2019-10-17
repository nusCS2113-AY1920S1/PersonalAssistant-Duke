package command;

import Dictionary.Word;
import Dictionary.WordBank;
import exception.NoWordFoundException;
import storage.Storage;
import ui.Ui;

import java.util.ArrayList;

/**
 * Represents a command from user to delete a task.
 * Inherits from Command class.
 */
public class EditCommand extends Command {

    protected String editedWord;
    protected String newMeaning;

    public EditCommand(String editedWord, String newMeaning) {
        this.editedWord = editedWord;
        this.newMeaning = newMeaning;
    }

    @Override
    public String execute(Ui ui, WordBank wordBank, Storage storage) {
        //ask ui to print something
        //ask tasks to store the thing in arraylist
        //ask storage to write to file
        try {
            // edit word
            String oldWordToString = wordBank.getWordBank().get(editedWord).toString();
            Word newWord = wordBank.getAndEditMeaning(editedWord,newMeaning);
            storage.editFromFile(oldWordToString,newWord.toString());
            String returned = ui.showEdited(newWord);
            return returned;

        } catch (NoWordFoundException e) {
            return e.showError();
        }
    }
}
