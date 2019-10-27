package command;

import dictionary.Bank;
import exception.NoWordFoundException;
import storage.Storage;
import ui.Ui;

import java.util.ArrayList;
import java.util.HashSet;

public class AddSynonymCommand extends Command {
    ArrayList<String> synonyms;
    String wordDescription;

    public AddSynonymCommand(String word, ArrayList<String> synonyms) {
        this.wordDescription = word;
        this.synonyms = synonyms;
    }

    @Override
    public String execute(Ui ui, Bank bank, Storage storage) {
        try {
            HashSet<String> synonymList = bank.addSynonym(wordDescription, synonyms);
            return ui.showAddTag(wordDescription, synonyms, synonymList);
        } catch (NoWordFoundException e) {
            return e.showError();
        }
    }
}
