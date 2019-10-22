package command;

import dictionary.Word;
import dictionary.WordBank;
import exception.NoWordFoundException;
import storage.Storage;
import ui.Ui;
import java.util.ArrayList;
import java.util.HashSet;

/**@author: Ng Jian Wei
 * Adds synonym to a word, and if the added-synonym is already in our dictionary
 * We add the main word into the synonymWord as its synonym
 */
public class AddSynonymCommand extends Command {
    ArrayList<String> synonyms =new ArrayList<String>();;
    String mainWord;
    public AddSynonymCommand(String mainWord, ArrayList<String> synonyms) {
        this.mainWord = mainWord;
        this.synonyms = synonyms;
    }

    @Override
    public String execute(Ui ui, WordBank wordBank, Storage storage) {
        try {
            HashSet<String> synonymList = wordBank.addSyn(mainWord, synonyms);
            String oldWordToString = wordBank.getWordBank().get(mainWord).toString();
            String[] oldWordToStringSplitter = oldWordToString.split("<s>"); // 3 parts
            if(oldWordToStringSplitter.length!=2){
                String temp = " <s>";
                for(String s : synonyms){
                    synonymList.add(s);
                    temp = temp + " " + s;
                }
                temp += " <s>";
                storage.editFromFile(oldWordToString,oldWordToString+temp);
                return ui.showAddSyn(mainWord, synonyms, synonymList);
            }
            else { //existence of synonym
                String oldSynoToString = oldWordToStringSplitter[1].trim(); //<s> old synos <s>
                String temp = " <s>";
                temp += oldSynoToString;
                for (String s : synonyms) {
                    synonymList.add(s);
                    temp = temp + " " + s;
                }
                temp += " <s>";
                storage.editFromFile(oldWordToString, oldWordToStringSplitter[0]+temp);
                String[] temp2 = wordBank.getWordBank().get(mainWord).toString().split("<s>",3);
                String[] temp3 =temp2[1].split(" ");
                //synonymList.clear();
                for(String s:temp3){
                    synonymList.add(s.trim());
                }
                return ui.showAddSyn(mainWord, synonyms, synonymList);
            }
        } catch (NoWordFoundException e) {
            return e.showError();
        }
    }

}
