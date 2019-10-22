package command;

import dictionary.WordBank;
import storage.Storage;
import ui.Ui;

/**
 * Represents the command from user to exit application.
 * Inherits from Command class.
 */

public class ExitCommand extends Command {

    @Override
    public String execute(Ui ui, WordBank wordBank, Storage storage) {
        System.exit(0);
        return null;
    }
}
