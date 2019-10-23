package dictionary;

import exception.NoWordFoundException;
import exception.WordAlreadyExistException;
import storage.Storage;

import java.util.ArrayList;
import java.util.HashSet;

public class Bank {
    WordBank wordBank;
    TagBank tagBank;

    public Bank(Storage storage) {
        wordBank = new WordBank(storage);
        tagBank = new TagBank();
    }

    public WordBank getWordBank() {
        return wordBank;
    }

    public TagBank getTagBank() {
        return tagBank;
    }

    public void addWord(Word word) throws WordAlreadyExistException {
        wordBank.addWord(word);
        tagBank.addWordAllTags(word);
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

    public Word getAndEditMeaning(String editedWord, String newMeaning) throws NoWordFoundException {
        return wordBank.getAndEditMeaning(editedWord, newMeaning);
    }

    public ArrayList<String> searchWordWithBegin(String begin) throws NoWordFoundException {
        return wordBank.searchWordWithBegin(begin);
    }

    public String searchForMeaning(String searchTerm) throws NoWordFoundException {
        return wordBank.searchForMeaning(searchTerm);
    }

    public void increaseSearchCount(String searchTerm) throws NoWordFoundException {
        wordBank.increaseSearchCount(searchTerm);
    }

    public ArrayList<String> getClosedWords(String searchTerm) {
        return wordBank.getClosedWords(searchTerm);
    }
}
