package command;

import dictionary.Bank;
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
    public String execute(Ui ui, Bank bank, Storage storage) {
        try {
            ArrayList<String> wordWithBegins = bank.searchWordWithBegin(this.begin);
            return ui.showSearchBegin(this.begin, wordWithBegins);
        } catch (NoWordFoundException e) {
            return e.showError();
        }
    }
}
