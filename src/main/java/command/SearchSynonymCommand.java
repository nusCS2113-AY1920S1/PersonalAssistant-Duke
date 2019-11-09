package command;

import dictionary.Bank;
import dictionary.Word;
import exception.NoSynonymFoundException;
import exception.NoWordFoundException;
import exception.SynonymBankEmptyException;
import storage.Storage;
import ui.Ui;

import java.util.HashSet;

public class SearchSynonymCommand extends Command {
    private String searchWord;
    private String type;

    public SearchSynonymCommand(String searchWord, String type){
        this.searchWord = searchWord;
        this.type = type;
    }

    @Override
    public String execute(Ui ui, Bank bank, Storage storage) {
        try {
                if (bank.synonymBankEmpty()) {
                    throw new SynonymBankEmptyException();
                }
                String[] words = bank.getWordsOfSynonym(searchWord);
                return ui.showSearchSynonym(searchWord, words);
        } catch (NoSynonymFoundException e) {
            try {
                return e.showError() + ui.showAllSynonyms(bank.getWordsOfSynonym(searchWord));
            }
            catch(NoSynonymFoundException f){
                return f.showError();
            }
        }catch (SynonymBankEmptyException e) {
            return e.showError();
        }
    }
}
