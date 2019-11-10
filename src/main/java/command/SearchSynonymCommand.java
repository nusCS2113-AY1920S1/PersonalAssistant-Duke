package command;

import dictionary.Bank;
import exception.NoSynonymFoundException;
import exception.SynonymBankEmptyException;
import storage.Storage;
import ui.Ui;

import java.util.ArrayList;

public class SearchSynonymCommand extends Command {
    private String searchWord;

    public SearchSynonymCommand(String searchWord){
        this.searchWord = searchWord;
    }

    @Override
    public String execute(Ui ui, Bank bank, Storage storage) {
        try {
            if (bank.synonymBankEmpty()) {
                throw new SynonymBankEmptyException();
            }
            ArrayList<String> words = bank.getSynonymsOfWord(searchWord);
            return ui.showSearchSynonym(searchWord, words);
        } catch (NoSynonymFoundException | SynonymBankEmptyException e) {
            return e.showError();
        }
    }
}