package command;
import Dictionary.Word;
import Dictionary.WordBank;
import exception.NoWordFoundException;
import storage.Storage;
import ui.Ui;
import java.util.*;

public class AddSynonymCommand extends Command {
    protected String queryWord;
    private ArrayList<String> synonyms;

    public AddSynonymCommand(String word, ArrayList<String>synonyms){
        this.queryWord = word;
        this.synonyms = synonyms;
    }
    @Override
    public String execute(Ui ui, WordBank wordBank, Storage storage) {
        try {
            HashSet<String> synonymList = wordBank.addSyn(queryWord, synonyms);
            /**Tentatively using showAddTag UI to test*/
            return ui.showAddTag(queryWord, synonyms, synonymList);
        }
        catch (NoWordFoundException e) {
            return e.showError();
        }
    }
}
