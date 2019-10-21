package command;

import dictionary.WordBank;
import exception.WordBankEmptyException;
import storage.Storage;
import ui.Ui;

/**
 * Represents a command from user to lists current tasks.
 * Inherits from Command class.
 */
public class ListCommand extends Command {

    protected String order;

    public ListCommand(String order) {
        this.order = order;
    }

    @Override
    public String execute(Ui ui, WordBank wordBank, Storage storage) {
        try {
            if (wordBank.getWordBank().isEmpty()) {
                throw new WordBankEmptyException();
            }
            return ui.showList(wordBank, this.order);
        } catch (WordBankEmptyException e) {
            return e.showError();
        }
    }
}
