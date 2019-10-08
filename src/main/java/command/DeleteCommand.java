package command;

import Dictionary.WordBank;
import exception.NoWordFoundException;
import storage.Storage;
import ui.Ui;

import java.util.ArrayList;

/**
 * Represents a command from user to delete a task.
 * Inherits from Command class.
 */
public class DeleteCommand extends Command {

    protected String deletedWord;
    protected ArrayList<String> tags;

    public DeleteCommand(String deletedWord) {
        this.deletedWord = deletedWord;
        this.tags = new ArrayList<>();
    }

    public DeleteCommand(String deletedWord, ArrayList<String> tags) {
        this.deletedWord = deletedWord;
        this.tags = tags;
    }

    @Override
    public void execute(Ui ui, WordBank wordBank, Storage storage) {
        //ask ui to print something
        //ask tasks to store the thing in arraylist
        //ask storage to write to file
        try {
            if (tags.size() == 0) {
                word = wordBank.getAndDelete(deletedWord);
                storage.deleteFromFile(word.toString());
                ui.showDeleted(word);
            }
            else {
                word = wordBank.getWordBank().get(deletedWord);
                ArrayList<String> nullTags = new ArrayList<>();
                ArrayList<String> deletedTags = new ArrayList<>();
                wordBank.deleteTags(deletedWord, tags, deletedTags, nullTags);
                ui.showDeletedTags(deletedWord, deletedTags);
                ui.showNullTags(deletedWord, nullTags);
            }
        } catch (NoWordFoundException e) {
            e.showError();
        }
    }
}
