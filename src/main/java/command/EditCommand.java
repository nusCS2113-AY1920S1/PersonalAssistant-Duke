package command;

import dictionary.Word;
import dictionary.WordBank;
import dictionary.WordCount;
import exception.NoWordFoundException;
import storage.Storage;
import ui.Ui;

/**
 * Represents a command from user to delete a task.
 * Inherits from Command class.
 */
public class EditCommand extends Command {

    protected String wordToBeEdited;
    protected String newMeaning;

    public EditCommand(String wordToBeEdited, String newMeaning) {
        this.wordToBeEdited = wordToBeEdited;
        this.newMeaning = newMeaning;
    }

    @Override
    public String execute(Ui ui, WordBank wordBank, Storage storage, WordCount wordCount) {
        try {
            Word oldWord = wordBank.getWord(wordToBeEdited); //get the original word
            wordBank.editWordMeaning(wordToBeEdited, newMeaning); //edit the word in the wordBank
            Word newWord = wordBank.getWord(wordToBeEdited); //get the new edited word
            storage.updateFile(oldWord.toString(), newWord.toString());
            String returned = ui.showEdited(newWord);
            return returned;

        } catch (NoWordFoundException e) {
            return e.showError();
        }
    }
}
