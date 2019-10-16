package command;

import Dictionary.Word;
import Dictionary.WordBank;
import storage.Storage;
import ui.Ui;

import java.util.Stack;

/**
 * Represents a command from user to delete a task.
 * Inherits from Command class.
 */
public class HistoryCommand extends Command {

    protected int numberOfWordsToDisplay;
    protected Stack<Word> wordHistory;

    public HistoryCommand(int numberOfWordsToDisplay) {
        this.numberOfWordsToDisplay = numberOfWordsToDisplay;
    }

    @Override
    public String execute(Ui ui, WordBank wordBank, Storage storage) {
        //ask ui to print something
        //ask tasks to store the thing in arraylist
        //ask storage to write to file
        wordHistory = storage.loadHistoryFromFile();
        ui.showHistory(wordHistory, numberOfWordsToDisplay);
        return "";
    }
}
