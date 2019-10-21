package command;

import dictionary.WordBank;
import dictionary.WordCount;
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
    public String execute(Ui ui, WordBank wordBank, Storage storage, WordCount wordCount) {
        return this.errorMessage;
    }

}
