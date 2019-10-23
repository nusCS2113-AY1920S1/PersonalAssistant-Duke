package command;

import dictionary.WordBank;
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
    public String execute(Ui ui, WordBank wordBank, Storage storage) {
        return ui.showHelp(this.instruction);
    }
}
