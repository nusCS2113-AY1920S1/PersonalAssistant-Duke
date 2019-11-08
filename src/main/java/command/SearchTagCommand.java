package command;

import dictionary.Bank;
import dictionary.Word;
import exception.NoTagFoundException;
import exception.NoWordFoundException;
import exception.TagBankEmptyException;
import storage.Storage;
import ui.Ui;

import java.util.HashSet;

/**
 * Searches for all words of a specific tag or all tags of a specific word.
 */
public class SearchTagCommand extends Command {
    private String searchTerm;
    private String type;

    public SearchTagCommand(String searchTerm, String type) {
        this.searchTerm = searchTerm;
        this.type = type;
    }

    @Override
    public String execute(Ui ui, Bank bank, Storage storage) {
        try {
            if (type.equals("tag")) {
                if (bank.tagBankEmpty()) {
                    throw new TagBankEmptyException();
                }
                String[] words = bank.getWordsOfTag(searchTerm);
                return ui.showSearchTag(searchTerm, words);
            } else {
                Word word = bank.getWordFromWordBank(searchTerm);
                HashSet<String> allTags = word.getTags();
                return ui.showTagsOfWord(searchTerm, allTags);
            }
        } catch (NoTagFoundException e) {
            return e.showError() + ui.showAllTags(bank.getAllTags());
        } catch (TagBankEmptyException | NoWordFoundException e) {
            return e.showError();
        }
    }
}
