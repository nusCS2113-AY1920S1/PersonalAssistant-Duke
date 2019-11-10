package command;

import dictionary.Bank;
import exception.NoWordFoundException;
import storage.Storage;
import ui.Ui;

import java.util.ArrayList;

public class AddSynonymCommand extends Command {
    String wordDescription;
    ArrayList<String> synonyms;

    public AddSynonymCommand(String wordDescription, ArrayList<String> synonyms) {
        this.wordDescription = wordDescription;
        this.synonyms = synonyms;
    }

    @Override
    public String execute(Ui ui, Bank bank, Storage storage) {
        try {
            ArrayList<String> synonymHashSet = bank.addWordToSomeSynonyms(wordDescription, synonyms);
            storage.writeSynonymBankExcelFile(bank.getSynonymBank());
            return ui.showAddSynonym(wordDescription, synonyms, synonymHashSet);
        } catch (NoWordFoundException e) {
            return e.showError();
        }
    }
}