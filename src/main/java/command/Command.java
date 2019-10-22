package command;

import dictionary.Word;
import dictionary.WordBank;
import storage.Storage;
import ui.Ui;

/**
 * Represents a general command from user.
 */
public abstract class Command {

    protected Word word;

    /**
     * Executes the command input of user.
     * @param ui UI class to interact with user
     * @param wordBank word bank to store all the words
     * @param storage File extracted to store all data
     * @return a string to show to user
     */
    public String execute(Ui ui, WordBank wordBank, Storage storage) {
        return null;
    }
}
