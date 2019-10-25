package command;

import dictionary.WordBank;
import dictionary.WordCount;
import storage.Storage;
import ui.Ui;


/**
 * Represents a command from user to see most/least searched words.
 * Inherits from Command class.
 */
public class SearchFrequencyCommand extends Command {

    protected String displayOrder;

    public SearchFrequencyCommand(String displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Override
    public String execute(Ui ui, WordBank wordBank, Storage storage, WordCount wordCount) {
        return ui.showSearchFrequency(wordCount, displayOrder);
    }
}