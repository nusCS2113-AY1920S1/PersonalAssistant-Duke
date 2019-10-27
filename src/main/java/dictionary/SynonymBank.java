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
     * Adds word to each synonym (since they are of the same meaning)
     * @param wordDescription word that need to be added
     * @param synonyms list of synonyms that will add the word
     */
    public void addSynonym(String wordDescription, ArrayList<String> synonyms) {
        for (String synonym : synonyms) { //traverse each synonym
            if (synonymBank.containsKey(synonym)) {
                synonymBank.get(synonym).add(wordDescription); //add the current word to that synonymWord
            } else {
                synonymBank.put(synonym, new HashSet<>(Collections.singletonList(wordDescription)));
            }
        }
    }

    /**
     * Adds a word to all synonyms in synonymBank of that word.
     * @param word word to add tag
     */
    public void addWordAllTags(Word word) {
        for (String tag : word.getTags()) {
            if (synonymBank.containsKey(tag)) {
                synonymBank.get(tag).add(word.getWord());
            } else {
                synonymBank.put(tag, new HashSet<>(Collections.singletonList(word.getWord())));
            }
        }
    }
}
