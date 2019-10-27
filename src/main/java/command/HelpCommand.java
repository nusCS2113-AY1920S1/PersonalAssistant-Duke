package command;

import dictionary.Bank;
import storage.Storage;
import ui.Ui;

/**
 * Represents the command from user to show instructions for usage.
 * Inherits from Command class.
 */

public class HelpCommand extends Command {

    protected String instruction;

    public HelpCommand(String instruction) {
        this.instruction = instruction;
    }

    @Override
    public String execute(Ui ui, Bank bank, Storage storage) {
        return ui.showHelp(this.instruction);
    }
}
