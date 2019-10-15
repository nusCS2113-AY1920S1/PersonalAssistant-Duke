package command;

import Dictionary.Word;
import Dictionary.WordBank;
import storage.Storage;
import ui.Ui;

/**
 * Represents a general command from user.
 */
public abstract class Command {

    protected Word word;

    public String execute(Ui ui, WordBank wordBank, Storage storage) {
        //ask ui to print something
        //ask storage to store the thing into tasks
        return null;
    }
}
