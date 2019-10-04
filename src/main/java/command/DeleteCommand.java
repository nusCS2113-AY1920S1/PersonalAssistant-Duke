package command;

import Dictionary.Word;
import Dictionary.WordBank;
import exception.DukeException;
import exception.NoWordFoundException;
import storage.Storage;
import ui.Ui;

/**
 * Represents a command from user to delete a task.
 * Inherits from Command class.
 * @author Zhang Yue Han
 */
public class DeleteCommand extends Command {

    public DeleteCommand(Word deletedWord) {
        this.word = deletedWord;
    }

    @Override
    public void execute(Ui ui, WordBank wordBank, Storage storage) {
        //ask ui to print something
        //ask tasks to store the thing in arraylist
        //ask storage to write to file
        try {
            wordBank.delete(word.getWord());
            ui.showDeleted(word);
        } catch (NoWordFoundException e) {
            e.showError();
        }
    }

}
