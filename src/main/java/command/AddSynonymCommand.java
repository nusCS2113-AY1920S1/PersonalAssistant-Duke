package command;
import dictionary.Word;
import dictionary.WordBank;
import exception.NoWordFoundException;
import storage.Storage;
import ui.Ui;
import java.util.*;

/**@author: Ng Jian Wei
 * Adds synonym to a word, and if the added-synonym is already in our dictionary
 * We add the main word into the synonymWord as its synonym
 */
public class AddSynonymCommand extends Command {
    ArrayList<String> synonyms;
    String mainWord;
    public AddSynonymCommand(String mainWord, ArrayList<String> synonyms) {
        this.mainWord = mainWord;
        this.synonyms = synonyms;
    }

    @Override
    public String execute(Ui ui, WordBank wordBank, Storage storage) {
        try {
            HashSet<String> synonymSet = wordBank.addSyn(mainWord, synonyms);
            String oldData = wordBank.getWordBank().get(mainWord).toString();
            System.out.println("oldData is "+oldData);
            synonymSet = wordBank.getWordBank().get(mainWord).getSynonyms();//Update
            String[] splitOldData = oldData.split("<s>");
            String temp = "<s>";
            for(String s:synonymSet){
                temp = temp + " "+s;
            }
            temp += "<s>";//lock
            storage.editFromFile(oldData,oldData+temp);
            return ui.showAddSyn(mainWord, synonyms, synonymSet);
        } catch (NoWordFoundException e) {
            return e.showError();
        }
    }
}
