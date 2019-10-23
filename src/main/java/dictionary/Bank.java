package dictionary;

import exception.NoWordFoundException;
import exception.WordAlreadyExistException;
import storage.Storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

public class Bank {
    WordBank wordBank;
    TagBank tagBank;

    public Bank(Storage storage) {
        wordBank = new WordBank(storage);
        tagBank = new TagBank();
    }

    public void addWord(Word word) throws WordAlreadyExistException {
        wordBank.addWord(word);
    }

    public HashSet<String> addTag(String wordDescription, ArrayList<String> tags) throws NoWordFoundException {
        HashSet<String> tagsOfWord = wordBank.addTag(wordDescription, tags);
        tagBank.addTag(wordDescription, tags);
        return tagsOfWord;
    }

    public Word getAndDelete(String deletedWord) throws NoWordFoundException {
        Word word = wordBank.getAndDelete(deletedWord);
        tagBank.deleteWordAllTags(word);
        return word;
    }


    public void deleteTags(String deletedWord, ArrayList<String> tags, ArrayList<String> deletedTags, ArrayList<String> nullTags) {
        wordBank.deleteTags(deletedWord, tags, deletedTags, nullTags);
        tagBank.deleteWordSomeTags(deletedTags, deletedWord);
    }

    public TreeMap<String, Word> getWordBank() {
        return wordBank.getWordBank();
    }

    public Word getAndEditMeaning(String editedWord, String newMeaning) throws NoWordFoundException {
        return wordBank.getAndEditMeaning(editedWord, newMeaning);
    }
}
