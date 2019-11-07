package command;

import dictionary.Bank;
import exception.TagBankEmptyException;
import exception.WordBankEmptyException;
import storage.Storage;
import ui.Ui;

public class ListTagCommand extends Command {
    @Override
    public String execute(Ui ui, Bank bank, Storage storage) {
        try {
            if (bank.tagBankEmpty()) {
                throw new TagBankEmptyException();
            }
            return ui.showAllTags(bank.getAllTags());
        } catch (TagBankEmptyException e) {
            return e.showError();
        }
    }
}