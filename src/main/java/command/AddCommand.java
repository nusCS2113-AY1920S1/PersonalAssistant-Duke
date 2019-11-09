package command;

import dictionary.Word;
import dictionary.Bank;
import exception.WordAlreadyExistsException;
import storage.Storage;
import ui.Ui;

/**
 * Represents a command from user to add a task.
 * Inherits from Command class.
 */
public class AddCommand extends Command {
    public AddCommand(Word w) {
        word = w;
    }

    @Override
    public String execute(Ui ui, Bank bank, Storage storage) {
        try {
            bank.addWordToBank(word);
            storage.writeFile(word.toString(), true, "wordup");
            storage.writeExcelFile(bank);
            return ui.showAdded(word);
        } catch (WordAlreadyExistsException e) {
            return e.showError();
        }
    }
}