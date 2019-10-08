package command;

import Dictionary.Word;
import Dictionary.WordBank;
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
        //ask ui to print something
        //add task to tasks
        //ask storage to write to file
        wordBank.addWord(word);
        storage.writeFile(word.toString(), true);
        return ui.showAdded(word);
    }
}
