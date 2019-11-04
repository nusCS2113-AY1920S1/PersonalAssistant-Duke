package command;

import dictionary.Bank;
import dictionary.Word;
import exception.NoWordFoundException;
import exception.WordBankEmptyException;
import exception.WordCountEmptyException;
import storage.Storage;
import ui.Ui;

import java.util.ArrayList;

/**
 * Represents a command from user to find tasks containing keywords specified.
 * Inherits from Command class.
 */
public class SearchCommand extends Command {

    protected String searchTerm;

    public SearchCommand(String queryWord) {
        this.searchTerm = queryWord.toLowerCase();
    }

    @Override
    public String execute(Ui ui, Bank bank, Storage storage) {
        try {
            String meaning = bank.searchWordBankForMeaning(this.searchTerm);
            Word word = bank.getWordBankObject().getWord(this.searchTerm);
            if (bank.getWordFromWordBank(searchTerm).getNumberOfSearches() == 0) {
                storage.updateFile(word.toString(),"");
                storage.writeFile(word.toString(),true);
                storage.writeWordBankExcelFile(bank.getWordBankObject());
            }
            bank.increaseSearchCount(searchTerm);
            return ui.showSearch(this.searchTerm, meaning);
        } catch (NoWordFoundException e) {
            ArrayList<String> arrayList = bank.getClosedWords(this.searchTerm);
            StringBuilder stringBuilder = new StringBuilder();
            if (arrayList.size() > 0) {
                stringBuilder.append("Are you looking for these words instead?\n");
            }
            for (int i = 0; i < arrayList.size(); i++) {
                stringBuilder.append(arrayList.get(i) + "\n");
            }
            return e.showError() + stringBuilder;
        } catch (WordBankEmptyException | WordCountEmptyException e) {
            return e.showError();
        }
    }
}