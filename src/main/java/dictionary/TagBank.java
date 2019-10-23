package dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class TagBank {
    HashMap<String, HashSet<String>> tagBank;

    public TagBank() {
        tagBank = new HashMap<>();
    }

    public void addTag(String wordDescription, ArrayList<String> tags) {
        for (String tag : tags) {
            tagBank.get(tag).add(wordDescription);
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
}
