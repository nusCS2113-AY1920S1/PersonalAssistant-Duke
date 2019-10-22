package command;

import dictionary.Word;
import dictionary.WordBank;
import storage.Storage;
import ui.Ui;

/**
 * Represents a command from user to add a task.
 * Inherits from Command class.
 */
public class AddCommand extends Command {
    public AddCommand(Word w) {
        word = w;
    }

    @Override
    public String execute(Ui ui, WordBank wordBank, Storage storage) {
        wordBank.addWord(word);
        storage.writeFile(word.toString(), true);
        return ui.showAdded(word);
    }
}
