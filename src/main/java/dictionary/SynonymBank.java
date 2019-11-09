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
    HashMap<String, String> synonymBank;

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
                if (!isSameSet(wordDescription, synonym)) {
                    Union(wordDescription, synonym);
                }
            } else {
                synonymBank.put(synonym, synonym);
                Union(wordDescription, synonym);
            }
        }
    }

    public int getSize() { return synonymBank.size(); }

    /**Delete a word from the list of synonyms belonging to one word.
     * Essentially this removes the selected synonym within the list
     * @param word word to be deleted
     */
/*
    public void deleteWordAllSynonyms(Word word) {
        for (String synonym : word.getSynonyms()) {
            synonymBank.get(synonym).remove(word.getWordString());
            if (synonymBank.get(synonym).size()==0)
                synonymBank.remove(synonym);
        }
    }
    */
    /**
     * Deletes a word from some synonyms of that word.
     *  We want to delete drink from beverage and alcohol only, leaving the rest stationary.
     * @param deletedSynonyms list of synonyms to delete the word
     * @param deletedWord word to be deleted
     */
    /*
    public void deleteWordSomeSynonyms(ArrayList<String> deletedSynonyms, String deletedWord) {
        for (String synonym : deletedSynonyms) {
            synonymBank.get(synonym).remove(deletedWord);
            if(synonymBank.get(synonym).size()==0)
                synonymBank.remove(synonym);
        }
    }
    */

    /**
     * Adds a word to all synonyms in SynonymBank of that the word has. Ensures that the synonymBank is updated.
     * @param word word to add synonym
     */

    public void addWordAllSynonyms(Word word) {
        for (String synonym : word.getSynonyms()) {
            if (synonymBank.containsKey(synonym)) {
                if (!isSameSet(word.getWordString(), synonym)) {
                    Union(word.getWordString(), synonym);
                }
            } else {
                synonymBank.put(synonym, synonym);
                Union(word.getWordString(), synonym);
            }
        }
    }

    /**
     * Adds a current word to all synonyms in SynonymBank of that the word has.
     * @param word word
     */
/*
    public void addWordToAllSynonyms(Word word) {
        for (String synonym : word.getSynonyms()) {
            if (synonymBank.containsKey(synonym)) {
                synonymBank.get(synonym).add(word.getWordString());
            } else {
                synonymBank.put(synonym, new HashSet<>(Collections.singletonList(word.getWordString())));
            }
        }
    }*/

    public String[] getAllSynonymsAsList() {
        return synonymBank.keySet().toArray(new String[synonymBank.size()]);
    }

    /**
     * Gets all words of a specific synonym as an array.
     * @param synonym a string represents the synonym
     * @return an array of words of that tag
     */
    public String[] getAllWordsOfSynonym(String synonym) {
        ArrayList<String> allSynonyms = new ArrayList<>();
        for (String s : synonymBank.keySet()) {
            if (!s.equals(synonym) && isSameSet(s, synonym)) {
                allSynonyms.add(s);
            }
        }
        return allSynonyms.toArray(new String[allSynonyms.size()]);
    }

    /**
     * Adds a word to one specific synonym in synonymBank.
     * @param word word to be added
     * @param synonym tag that the word will be added to
     */
    public void addWordToOneSynonym(String word, String synonym) {
        if (!synonymBank.containsKey(word)) {
            synonymBank.put(word, word);
        }
        if (!synonymBank.containsKey(synonym)) {
            synonymBank.put(synonym, synonym);
        }
        if (!isSameSet(word, synonym)) {
            Union(word, synonym);
        }
    }

    private void Union(String word, String synonym) {
        synonymBank.replace(synonym, synonymBank.get(word));
    }

    private boolean isSameSet(String word, String synonym) {
        return findSet(word).equals(findSet(synonym));
    }

    private String findSet(String word) {
        if (synonymBank.get(word).equals(word)) {
            return word;
        }
        return synonymBank.replace(word, findSet(synonymBank.get(word)));
    }

    public boolean isEmpty() {
        return synonymBank.isEmpty();
    }

    public boolean contains(String searchTag) {
        return synonymBank.containsKey(searchTag);
    }
}