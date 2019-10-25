package command;

import dictionary.Bank;
import storage.Storage;
import ui.Ui;

/**
 * Represents a command from system to abort task due to bad user command.
 * Inherits from Command class.
 */
public class BadCommand extends Command {

    String errorMessage;

    public BadCommand(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String execute(Ui ui, Bank bank, Storage storage) {
        return this.errorMessage;
    }

}
