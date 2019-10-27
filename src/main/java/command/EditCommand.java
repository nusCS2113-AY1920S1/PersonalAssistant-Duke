package command;

import dictionary.Word;
import dictionary.Bank;
import exception.NoWordFoundException;
import storage.Storage;
import ui.Ui;

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
    public String execute(Ui ui, Bank bank, Storage storage) {
        try {
            // edit word
            Word newWord = bank.getAndEditMeaning(editedWord, newMeaning);
            storage.writeWordBankExcelFile(bank.getWordBank());
            return ui.showEdited(newWord);
        } catch (NoWordFoundException e) {
            return e.showError();
        }
    }
}
