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
     */
    public void joinSynonymWords(String wordDescription, ArrayList<String> synonyms) {
        if (!synonymBank.containsKey(wordDescription)) {
            synonymBank.put(wordDescription, wordDescription);
        }
        for (String synonym : synonyms) {
            if (synonymBank.containsKey(synonym)) {
                if (!isSameSet(wordDescription, synonym)) {
                    _Union(wordDescription, synonym);
                }
            } else {
                synonymBank.put(synonym, synonym);
                _Union(wordDescription, synonym);
            }
        }
    }

    public int getSize() {
        return synonymBank.size();
    }

    /**
     * Gets all sets of words that have the same meaning in the synonym bank.
     * @return set of words that have the same meaning
     */
    public ArrayList<ArrayList<String>> getAllSynonymsAsList() {
        ArrayList<ArrayList<String>> arrayList = new ArrayList<>();
        HashSet<String> roots = new HashSet<>();
        for (String word : synonymBank.keySet()) {
            String parent = findSet(word);
            if (!roots.contains(parent)) {
                ArrayList<String> temp = getAllSynonymsOfWord(word);
                temp.add(word);
                arrayList.add(temp);
                roots.add(parent);
            }
        }
        return arrayList;
    }

    /**
     * Gets all synonyms of a specific word as an array.
     * @param synonym a string represents the synonym
     * @return an array of words of that tag
     */
    public ArrayList<String> getAllSynonymsOfWord(String synonym) {
        ArrayList<String> allSynonyms = new ArrayList<>();
        for (String s : synonymBank.keySet()) {
            if (!s.equals(synonym) && isSameSet(s, synonym)) {
                allSynonyms.add(s);
            }
        }
        return allSynonyms;
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
            _Union(word, synonym);
        }
    }

    private void _Union(String word, String synonym) {
        synonymBank.replace(findSet(synonym), synonymBank.get(findSet(word)));
    }

    private boolean isSameSet(String word, String synonym) {
        return findSet(word).equals(findSet(synonym));
    }

    /**
     * Finds the root word that have the same meaning with the current word.
     * @param word word to be looked for
     * @return root word of the current word
     */
    private String findSet(String word) {
        if (synonymBank.get(word).equals(word)) {
            return word;
        }
        String newParent = findSet(synonymBank.get(word));
        synonymBank.replace(word, newParent);
        return newParent;
    }

    public boolean isEmpty() {
        return synonymBank.isEmpty();
    }

    public boolean contains(String searchTag) {
        return synonymBank.containsKey(searchTag);
    }
}