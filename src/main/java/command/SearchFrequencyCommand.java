package command;

import dictionary.Bank;
import storage.Storage;
import ui.Ui;


/**
 * Represents a command from user to see most/least searched words.
 * Inherits from Command class.
 */
public class SearchFrequencyCommand extends Command {

    protected String displayOrder;

    public SearchFrequencyCommand(String displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Override
    public String execute(Ui ui, Bank bank, Storage storage) {
        return ui.showSearchFrequency(bank.getWordCountObject(), displayOrder);
    }
}