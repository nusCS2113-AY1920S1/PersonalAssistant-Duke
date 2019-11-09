package command;

import dictionary.Bank;
import exception.NoWordFoundException;
import storage.Storage;
import ui.Ui;

import java.util.ArrayList;
import java.util.HashSet;

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
            HashSet<String> synonymHashSet = bank.addWordToSomeSynonyms(wordDescription, synonyms);
            storage.writeSynonymBankExcelFile(bank.getSynonymBank());
            return ui.showAddSynonym(wordDescription, synonyms, synonymHashSet);
        } catch (NoWordFoundException e) {
            return e.showError();
        }
    }
}
