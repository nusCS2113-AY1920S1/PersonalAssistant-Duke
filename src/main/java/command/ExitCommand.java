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
        //ask ui to print something
        //ask tasks to store the thing in arraylist
        //ask storage to write to file
        System.exit(0);
        return null;
    }
}
