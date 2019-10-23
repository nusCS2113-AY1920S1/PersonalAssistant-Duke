package dictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class TagBank {
    HashMap<String, HashSet<String>> tagBank;

    public TagBank() {
        tagBank = new HashMap<>();
    }

    public void addTag(String wordDescription, ArrayList<String> tags) {
        for (String tag : tags) {
            if (tagBank.containsKey(tag)) {
                tagBank.get(tag).add(wordDescription);
            }
            else {
                tagBank.put(tag, new HashSet<>(Collections.singletonList(wordDescription)));
            }
        }
    }

    public void deleteWordAllTags(Word word) {
        for (String tag : word.getTags()) {
            tagBank.get(tag).remove(word.getWord());
        }
    }

    public void deleteWordSomeTags(ArrayList<String> deletedTags, String deletedWord) {
        for (String tag : deletedTags) {
            tagBank.get(tag).remove(deletedWord);
        }
    }

    public void addWordAllTags(Word word) {
        for (String tag : word.getTags()) {
            if (tagBank.containsKey(tag)) {
                tagBank.get(tag).add(word.getWord());
            }
            else {
                tagBank.put(tag, new HashSet<>(Collections.singletonList(word.getWord())));
            }
        }
    }
}
