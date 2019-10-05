package command;

import Dictionary.WordBank;
import storage.Storage;
import ui.Ui;

/**
 * Represents the command from user to exit application.
 * Inherits from Command class.
 * @author Zhang Yue Han
 */

public class ExitCommand extends Command {

    @Override
    public void execute(Ui ui, WordBank wordBank, Storage storage) {
        //ask ui to print something
        //ask tasks to store the thing in arraylist
        //ask storage to write to file
        ui.goodBye();
        isExit();
    }

    /**
     * Changes the exit boolean to be true.
     * @return the value of exit as true
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
