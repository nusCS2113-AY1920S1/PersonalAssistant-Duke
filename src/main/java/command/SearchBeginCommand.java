package command;

import dictionary.WordBank;
import exception.NoWordFoundException;
import storage.Storage;
import ui.Ui;

import java.util.ArrayList;

public class SearchBeginCommand extends Command {
    String begin;

    public SearchBeginCommand(String begin) {
        this.begin = begin;
    }

    @Override
    public String execute(Ui ui, WordBank wordBank, Storage storage) {
        try {
            ArrayList<String> wordWithBegins = wordBank.searchWordWithBegin(this.begin);
            return ui.showSearchBegin(this.begin, wordWithBegins);
        } catch (NoWordFoundException e) {
            return e.showError();
        }
    }
}
