package dictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class SynonymBank {
    HashMap<String, HashSet<String>> synonymBank;

    public SynonymBank() {
        synonymBank = new HashMap<>();
    }

    /**
     * Adds a word to all synonyms present in the list
     * @param wordDescription word that need to be added
     * @param synonyms list that will add the word
     * @author Ng Jian Wei
     */
    public void addSynonym(String wordDescription, ArrayList<String> synonyms) {
        for (String synonym : synonyms) {
            if (synonymBank.containsKey(synonym)) {
                synonymBank.get(synonym).add(wordDescription);
            } else { //if not exist, make an entry for this current synonym, insert wordDescription to the HashSet<String>
                synonymBank.put(synonym, new HashSet<>(Collections.singletonList(wordDescription)));
            }
        }
    }
    /**Delete a word from the list of synonyms belonging to one word
     * Essentially this removes the selected synonym within the list
     * @param word word to be deleted
     */
    public void deleteWordAllSynonyms(Word word) {
        for (String synonym : word.getSynonyms()) { //if word is drink , synonyms are beverage , alcohol
            synonymBank.get(synonym).remove(word.getWordString()); //for each synonym beverage,alcohol etc, remove drink from their syno list
        }
    }

    /**
     * Deletes a word from some synonyms of that word.
     * @param deletedSynonyms list of synonyms to delete the word
     * @param deletedWord word to be deleted
     * Using the previous example where drink has synonyms of beverage, alcohol and some other synonyms
     * We want to delete drink from beverage and alcohol only, leaving the rest stationary
     */
    public void deleteWordSomeSynonyms(ArrayList<String> deletedSynonyms, String deletedWord) {
        for (String synonym : deletedSynonyms) { //deletedSynonyms contains beverage and alcohol
            synonymBank.get(synonym).remove(deletedWord); //look up these synonyms and remove the deletedWord
        }
    }

    /**
     * Adds a word to all synonyms in SynonymBank of that the word has.
     * @param word word to add synonym
     * This feature basically cross adds, if A is a synonym of B, then B must be a synonym of A
     * Ensures that the synonymBank is updated
     */
    public void addWordAllSynonyms(Word word) {
        for (String synonym : word.getSynonyms()) {
            if (synonymBank.containsKey(synonym)) {
                synonymBank.get(synonym).add(word.getWordString());
            } else {
                synonymBank.put(synonym, new HashSet<>(Collections.singletonList(word.getWordString())));
            }
        }
    }
}