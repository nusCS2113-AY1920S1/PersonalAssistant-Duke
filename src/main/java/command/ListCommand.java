package command;

import dictionary.Bank;
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
    public String execute(Ui ui, Bank bank, Storage storage) {
        try {
            if (bank.getWordBank().isEmpty()) {
                throw new WordBankEmptyException();
            }
            return ui.showList(bank.getWordBank().getWordBank(), this.order);
        } catch (WordBankEmptyException e) {
            return e.showError();
        }
    }
}
