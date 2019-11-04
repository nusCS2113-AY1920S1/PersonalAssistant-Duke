package command;

import dictionary.Bank;
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
    protected ArrayList<String> synonyms;

    /**
     * Sets the string for deletion
     * @param deletedWord
     */
    public DeleteCommand(String deletedWord) {
        this.deletedWord = deletedWord;
        this.tags = new ArrayList<>();
        this.synonyms = new ArrayList<>();
    }

    public DeleteCommand(String deletedWord, ArrayList<String> tags) {
        this.deletedWord = deletedWord;
        this.tags = tags;
    }

    @Override
    public String execute(Ui ui, Bank bank, Storage storage) {
        try {
            word = bank.getWordFromWordBank(this.deletedWord);
            if (tags.size() == 0) {                     //delete word
                bank.deleteWordFromBank(word);
                storage.updateFile(word.toString() + "\r","");
                return ui.showDeleted(word);
            } else {                                    //delete tags
                ArrayList<String> nullTags = new ArrayList<>();
                ArrayList<String> deletedTags = new ArrayList<>();
                bank.deleteTags(deletedWord, tags, deletedTags, nullTags);
                String returned = ui.showDeletedTags(deletedWord, deletedTags);
                returned += ui.showNullTags(deletedWord, nullTags);
                return returned;
            }
        } catch (NoWordFoundException e) {
            return e.showError();
        }
    }
}