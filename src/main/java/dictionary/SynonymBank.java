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
     * Adds a word to all synonyms present in the list.
     * @param wordDescription word that need to be added
     * @param synonyms list that will add the word
     * @author Ng Jian Wei
     */
    public void addSynonym(String wordDescription, ArrayList<String> synonyms) {
        for (String synonym : synonyms) {
            if (synonymBank.containsKey(synonym)) {
                synonymBank.get(synonym).add(wordDescription);
            } else {
                synonymBank.put(synonym, new HashSet<>(Collections.singletonList(wordDescription)));
            }
        }
    }
    /**Delete a word from the list of synonyms belonging to one word.
     * Essentially this removes the selected synonym within the list
     * @param word word to be deleted
     */

    public void deleteWordAllSynonyms(Word word) {
        for (String synonym : word.getSynonyms()) {
            synonymBank.get(synonym).remove(word.getWordString());
        }
    }
    /**
     * Deletes a word from some synonyms of that word.
     *  We want to delete drink from beverage and alcohol only, leaving the rest stationary.
     * @param deletedSynonyms list of synonyms to delete the word
     * @param deletedWord word to be deleted
     */

    public void deleteWordSomeSynonyms(ArrayList<String> deletedSynonyms, String deletedWord) {
        for (String synonym : deletedSynonyms) {
            synonymBank.get(synonym).remove(deletedWord); //look up these synonyms and remove the deletedWord
        }
    }
    /**
     * Adds a word to all synonyms in SynonymBank of that the word has. Ensures that the synonymBank is updated.
     * @param word word to add synonym
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