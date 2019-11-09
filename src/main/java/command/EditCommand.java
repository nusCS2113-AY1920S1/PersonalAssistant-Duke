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

    protected String wordToBeEdited;
    protected String newMeaning;

    public EditCommand(String wordToBeEdited, String newMeaning) {
        this.wordToBeEdited = wordToBeEdited;
        this.newMeaning = newMeaning;
    }

    @Override
    public String execute(Ui ui, Bank bank, Storage storage) {
        try {
            String oldString = bank.getWordFromWordBank(wordToBeEdited).toString(); //get the original word
            Word newWord = bank.editWordMeaning(wordToBeEdited, newMeaning); //edit the word in the wordBank
            storage.writeWordBankExcelFile(bank.getWordBankObject());
            storage.updateFile(oldString, newWord.toString());
            return ui.showEdited(newWord);
        } catch (NoWordFoundException e) {
            return e.showError();
        }
    }
}