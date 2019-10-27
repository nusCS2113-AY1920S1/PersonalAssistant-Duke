package command;

import dictionary.Word;
import dictionary.Bank;
import exception.WordAlreadyExistException;
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
            bank.addWord(word);
            storage.writeFile(word.toString(), true);
            return ui.showAdded(word);
        } catch (WordAlreadyExistException e) {
            return e.showError();
        }
    }
}
