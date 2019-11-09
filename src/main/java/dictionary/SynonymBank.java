package dictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/*The synonymBank works as follows
Whenever we add synonyms to any word,
the synonym itself will fill up the first column within the excel data structure
The word will be in the second column
 */
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
    public void addWordToSomeSynonyms(String wordDescription, ArrayList<String> synonyms) {
        for (String synonym : synonyms) {
            if (synonymBank.containsKey(synonym)) {
                synonymBank.get(synonym).add(wordDescription);
            } else {
                synonymBank.put(synonym, new HashSet<>(Collections.singletonList(wordDescription)));
            }
        }
    }

    public int getSize() { return synonymBank.size(); }

    /**Delete a word from the list of synonyms belonging to one word.
     * Essentially this removes the selected synonym within the list
     * @param word word to be deleted
     */

    public void deleteWordAllSynonyms(Word word) {
        for (String synonym : word.getSynonyms()) {
            synonymBank.get(synonym).remove(word.getWordString());
            if (synonymBank.get(synonym).size()==0)
                synonymBank.remove(synonym);
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
            synonymBank.get(synonym).remove(deletedWord);
            if(synonymBank.get(synonym).size()==0)
                synonymBank.remove(synonym);
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

    /**
     * Adds a current word to all synonyms in SynonymBank of that the word has.
     * @param word word
     */
    public void addWordToAllSynonyms(Word word) {
        for (String synonym : word.getSynonyms()) {
            if (synonymBank.containsKey(synonym)) {
                synonymBank.get(synonym).add(word.getWordString());
            } else {
                synonymBank.put(synonym, new HashSet<>(Collections.singletonList(word.getWordString())));
            }
        }
    }

    public String[] getAllSynonymsAsList() {
        return synonymBank.keySet().toArray(new String[synonymBank.size()]);
    }

    /**
     * Gets all words of a specific synonym as an array.
     * @param synonym a string represents the synonym
     * @return an array of words of that tag
     */
    public String[] getAllWordsOfSynonym(String synonym) {
        HashSet<String> allWords = synonymBank.get(synonym);
        return allWords.toArray(new String[allWords.size()]);
    }

    /**
     * Adds a word to one specific synonym in synonymBank.
     * @param word word to be added
     * @param synonym tag that the word will be added to
     */
    public void addWordToOneSynonym(String word, String synonym) {
        if (synonymBank.containsKey(synonym)) {
            synonymBank.get(synonym).add(word);
        } else {
            synonymBank.put(synonym, new HashSet<>(Collections.singletonList(word)));
        }
    }

    public boolean isEmpty() {
        return synonymBank.isEmpty();
    }

    public boolean contains(String searchTag) {
        return synonymBank.containsKey(searchTag);
    }
}