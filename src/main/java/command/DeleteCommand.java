package command;

import dictionary.WordBank;
import dictionary.WordCount;
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
    public String execute(Ui ui, WordBank wordBank, Storage storage, WordCount wordCount) {
        try {
            word = wordBank.getWord(this.deletedWord);
            if (tags.size() == 0) {                     //delete word
                wordBank.deleteWord(word);
                wordCount.deleteWord(word);
                storage.updateFile(word.toString() + "\r","");
                return ui.showDeleted(word);
            } else {                                    //delete tags
                wordCount.deleteWord(word);
                ArrayList<String> nullTags = new ArrayList<>();
                ArrayList<String> deletedTags = new ArrayList<>();
                wordBank.deleteTags(deletedWord, tags, deletedTags, nullTags);
                String returned = ui.showDeletedTags(deletedWord, deletedTags);
                returned += ui.showNullTags(deletedWord, nullTags);
                return returned;
            }
        } catch (NoWordFoundException e) {
            return e.showError();
        }
    }
}
