package dictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

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