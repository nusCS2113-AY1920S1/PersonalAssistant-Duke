package command;

import Dictionary.Word;
import Dictionary.WordBank;
import storage.Storage;
import ui.Ui;

import java.util.Stack;

/**
 * Represents a command from user to see most/least searched words.
 * Inherits from Command class.
 */
public class FrequentlySearchedCommand extends Command {

    protected String order;

    public FrequentlySearchedCommand(String order) {
        this.order = order;
    }

    @Override
    public String execute(Ui ui, WordBank wordBank, Storage storage) {
        //ask ui to print something
        //ask tasks to store the thing in arraylist
        //ask storage to write to file
        return ui.showSearchFrequency(wordBank,order);
    }
}