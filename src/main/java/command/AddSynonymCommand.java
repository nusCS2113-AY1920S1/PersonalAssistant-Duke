package command;

import dictionary.Bank;
import dictionary.Word;
import exception.NoWordFoundException;
import exception.WordBankEmptyException;
import storage.Storage;
import ui.Ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

public class AddSynonymCommand extends Command {
    String wordDescription;
    ArrayList<String> synonyms;
    private static int counter = 0;

    public AddSynonymCommand(String wordDescription, ArrayList<String> synonyms) {
        this.wordDescription = wordDescription;
        this.synonyms = synonyms;
    }

    @Override
    public String execute(Ui ui, Bank bank, Storage storage) {
        try {
            if(counter!=0){
                System.out.println("counter is "+counter);
                TreeMap<String, Word> wordBankData = bank.getWordBankData();
                for(String s : synonyms){
                    if(!wordBankData.containsKey(s)){
                        System.out.println("im in here");
                        synonyms.remove(s);
                        System.out.println("i reached here");
                    }
                }
                System.out.println("Final synonyms list is ");
                for(String s: synonyms){
                    System.out.println(s);
                }
                System.out.println("byebye");
            }
            counter++;
            System.out.println("byebye");
            HashSet<String> synonymHashSet = bank.addWordToSomeSynonyms(wordDescription, synonyms);
            storage.writeSynonymBankExcelFile(bank.getSynonymBank());
            return ui.showAddSynonym(wordDescription, synonyms, synonymHashSet);
        } catch (NoWordFoundException e) {
            return e.showError();
        }
    }
}
