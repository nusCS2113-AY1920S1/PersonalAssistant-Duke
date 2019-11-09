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
            if (type.equals("synonym")) {
                if (bank.tagBankEmpty()) {
                    throw new SynonymBankEmptyException();
                }
                String[] words = bank.getWordsOfSynonym(searchWord);
                return ui.showSearchTag(searchWord, words);
            } else {
                Word word = bank.getWordFromWordBank(searchWord);
                HashSet<String> allTags = word.getTags();
                return ui.showTagsOfWord(searchWord, allTags);
            }
        } catch (NoSynonymFoundException e) {
            return e.showError() + ui.showAllSynonyms(bank.getAllSynonyms());
        } catch (SynonymBankEmptyException | NoWordFoundException e) {
            return e.showError();
        }
    }
}
