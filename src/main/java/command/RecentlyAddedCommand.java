package command;

import dictionary.Word;
import dictionary.Bank;
import storage.Storage;
import ui.Ui;

import java.util.Stack;

/**
 * Represents a command from user to see recently added words.
 * Inherits from Command class.
 */
public class RecentlyAddedCommand extends Command {

    protected int numberOfWordsToDisplay;
    protected Stack<Word> wordHistory;

    public RecentlyAddedCommand(int numberOfWordsToDisplay) {
        this.numberOfWordsToDisplay = numberOfWordsToDisplay;
    }

    @Override
    public String execute(Ui ui, Bank bank, Storage storage) {
        wordHistory = storage.loadHistoryFromFile();
        return ui.showHistory(wordHistory, numberOfWordsToDisplay);
    }
}
