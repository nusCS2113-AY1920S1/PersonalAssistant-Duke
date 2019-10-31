package command;

import dictionary.Bank;
import dictionary.Word;
import exception.NoWordFoundException;
import exception.WordBankEmptyException;
import exception.WordCountEmptyException;
import storage.Storage;
import ui.Ui;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a command from user to find tasks containing keywords specified.
 * Inherits from Command class.
 */
public class SearchCommand extends Command {

    protected String searchTerm;

    public SearchCommand(String queryWord) {
        this.searchTerm = queryWord;
    }

    @Override
    public String execute(Ui ui, Bank bank, Storage storage) {
        try {
            String meaning = bank.searchWordBankForMeaning(this.searchTerm);
            Word word = bank.getWordBankObject().getWord(this.searchTerm);
            if (bank.getWordFromWordBank(searchTerm).getNumberOfSearches() == 0) {
                storage.updateFile(word.toString(),"");
                storage.writeFile(word.toString(),true);
            }
            bank.increaseSearchCount(searchTerm);
            return ui.showSearch(this.searchTerm, meaning);
        } catch (NoWordFoundException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to locate \"" + searchTerm
                    + "\" in local dictionary.\nLooking up Oxford dictionary.\n\n");
            try {
                String result = OxfordCall.onlineSearch(searchTerm);
                stringBuilder.append(ui.showSearch(this.searchTerm, result));
            } catch (NoWordFoundException e2) {
                stringBuilder.append("Failed to find the word from Oxford dictionary.\n");
            }

            ArrayList<String> arrayList = bank.getClosedWords(this.searchTerm);

            if (arrayList.size() > 0) {
                stringBuilder.append("\nAre you looking for these words instead?\n");
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