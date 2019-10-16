package Dictionary;

import java.util.ArrayList;

import exception.NoWordFoundException;
import command.OxfordCall;
import storage.Storage;

import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class WordBank {
    private TreeMap<String, Word> wordBank;
    private TreeMap<Word, Integer> wordCount;

    public WordBank(Storage storage) {
        wordBank = storage.loadFile();
    }

    public WordBank(TreeMap<String, Word> wordBank) {
        this.wordBank = wordBank;
    }

    public TreeMap<String, Word> getWordBank() {
        return wordBank;
    }

    public TreeMap<Word, Integer> getWordCount() {
        return wordCount;
    }

    public Word getWordAndMeaning(String word) throws NoWordFoundException {
        if (!wordBank.containsKey(word)) {
            throw new NoWordFoundException(word);
        }
        return wordBank.get(word);
    }

    public void addWord(Word word) {
        this.wordBank.put(word.getWord(), word);
    }

    /**
     * Looks up for meaning of a specific word
     * @param word word to be searched for its meaning
     * @return a string represents meaning of that word
     * @throws NoWordFoundException if the word doesn't exist in the word bank nor Oxford dictionary
     */
    public String searchForMeaning(String word)throws NoWordFoundException {
        word = word.toLowerCase();
        String s = "";
        if (!(wordBank.containsKey(word))){
            s = "Unable to locate \""+word+"\" in local dictionary. Looking up Oxford Dictionary\n";
            String result = OxfordCall.onlineSearch(word);
            Word temp = new Word(word, result);
            wordBank.put(word, temp);
        }
        return s + wordBank.get(word).getMeaning();
    }

    /**
     * Updates the meaning of a specific word.
     * @param wordToBeEdited word whose meaning is updated
     * @param newMeaning new meaning of the word
     * @throws NoWordFoundException if the word doesn't exist in the word bank
     */
    public void editMeaning(String wordToBeEdited, String newMeaning) throws NoWordFoundException{
        if (wordBank.containsKey(wordToBeEdited)) {
            wordBank.get(wordToBeEdited).editMeaning(newMeaning);
        }
        else {
            throw new NoWordFoundException(wordToBeEdited);
        }
    }

    /**
     * Increases the search count for a word.
     * @param searchedWord word that is being searched for by the user
     * @throws NoWordFoundException if word does not exist in the word bank
     */
    public void increaseSearchCount(String searchedWord) throws NoWordFoundException{
        if (wordBank.containsKey(searchedWord)) {
            wordBank.get(searchedWord).incrementNumberOfSearches();
        }
        else {
            throw new NoWordFoundException(searchedWord);
        }
    }

    public Word getAndDelete(String word) throws NoWordFoundException {
        if (wordBank.containsKey(word)) {
            return wordBank.remove(word);
        }
        else {
            throw new NoWordFoundException(word);
        }
    }

    /**
     * Adds a tag to a specific word in word bank.
     * @param wordToBeAddedTag word that the tag is set for
     * @param tags new tags input by user
     * @return tags lists of that word
     * @throws NoWordFoundException if the word doesn't exist in the word bank
     */
    public HashSet<String> addTag(String wordToBeAddedTag, ArrayList<String> tags) throws NoWordFoundException {
        if (!wordBank.containsKey(wordToBeAddedTag)) {
            throw new NoWordFoundException(wordToBeAddedTag);
        }
        Word word = wordBank.get(wordToBeAddedTag);
        for (String tag : tags) {
            word.addTag(tag);
        }
        return word.getTags();
    }

    public String getBankData() {
        StringBuilder data = new StringBuilder();
        for (Map.Entry<String, Word> entry : wordBank.entrySet()) {
            data.append(entry.getValue() + "\n");
        }
        return data.toString();
    }

    public void deleteTags(String word, ArrayList<String> tagList, ArrayList<String> deletedTags, ArrayList<String> nonExistTags) {
        HashSet<String> tags = wordBank.get(word).getTags();
        for (String tag : tagList) {
            if (tags.contains(tag)) {
                tags.remove(tag);
                deletedTags.add(tag);
            }
            else {
                nonExistTags.add(tag);
            }
        }
    }
}
