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
            Word oldWord = bank.getWordFromWordBank(wordToBeEdited); //get the original word
            bank.editWordMeaning(wordToBeEdited, newMeaning); //edit the word in the wordBank
            Word newWord = bank.getWordFromWordBank(wordToBeEdited); //get the new edited word
            storage.writeWordBankExcelFile(bank.getWordBankObject());
            storage.updateFile(oldWord.toString(), newWord.toString(), "wordup");
            return ui.showEdited(newWord);

        } catch (NoWordFoundException e) {
            return e.showError();
        }
    }
}