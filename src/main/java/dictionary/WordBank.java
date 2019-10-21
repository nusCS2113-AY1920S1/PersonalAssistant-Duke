package dictionary;

import java.util.ArrayList;

import exception.NoWordFoundException;
import command.OxfordCall;
import storage.Storage;

import java.util.HashSet;
import java.util.TreeMap;
import java.util.Set;

public class WordBank {
    private TreeMap<String, Word> wordBank;

    public WordBank(Storage storage) {
        wordBank = storage.loadFile();
    }

    public WordBank(TreeMap<String, Word> wordBank) {
        this.wordBank = wordBank;
    }

    public TreeMap<String, Word> getWordBank() {
        return wordBank;
    }

    public void addWord(Word word) {
        this.wordBank.put(word.getWord(), word);
    }

    /**
     * Looks up for meaning of a specific word.
     * @param word word to be searched for its meaning
     * @return a string represents meaning of that word
     * @throws NoWordFoundException if the word doesn't exist in the word bank nor Oxford dictionary
     */
    public String searchForMeaning(String word)throws NoWordFoundException {
        word = word.toLowerCase();
        String s = "";
        if (!(wordBank.containsKey(word))) {
            s = "Unable to locate \"" + word + "\" in local dictionary. Looking up Oxford dictionary\n";
            String result = OxfordCall.onlineSearch(word);
            Word temp = new Word(word, result);
            wordBank.put(word, temp);
        }
        return s + wordBank.get(word).getMeaning();
    }

    /**
     * Updates the meaning of a specific word.
     * @param wordToBeEdited word whose meaning is updated
     * @throws NoWordFoundException if the word doesn't exist in the word bank
     */
    public Word getAndEditMeaning(String wordToBeEdited, String newMeaning) throws NoWordFoundException {
        if (wordBank.containsKey(wordToBeEdited)) {
            wordBank.get(wordToBeEdited).editMeaning(newMeaning);
            return wordBank.get(wordToBeEdited);
        } else {
            throw new NoWordFoundException(wordToBeEdited);
        }
    }

    /**
     * Deletes a word with a specific description and return it.
     * @param word string represents a word to be deleted
     * @return the word itself
     * @throws NoWordFoundException if the word doesn't exist in the word bank
     */
    public Word getAndDelete(String word) throws NoWordFoundException {
        if (wordBank.containsKey(word)) {
            return wordBank.remove(word);
        } else {
            throw new NoWordFoundException(word);
        }
    }

    /**
     * Adds a tag to a specific word in word bank.
     * @param wordToBeAddedTag word that the tag is set for
     * @param tags new tags input by user
     * @return tag lists of that word
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
    /**
     * Adds synonyms to a specific word in word bank
     */
    public HashSet<String> addSyn(String wordKey, ArrayList<String> synonymsWords) throws NoWordFoundException {
        if(!wordBank.containsKey(wordKey)){
            throw new NoWordFoundException(wordKey);
        }
        Word word = wordBank.get(wordKey);
        for(String synoWord : synonymsWords){
            word.addSyn(synoWord);
        }
        return word.getSynonyms();
    }

    /**
     * Deletes tags from a word.
     * @param word string represent the word
     * @param tagList list of tags to be deleted
     * @param deletedTags tags to be deleted
     * @param nonExistTags tags that doesn't exist in the word
     */
    public void deleteTags(String word, ArrayList<String> tagList,
                           ArrayList<String> deletedTags, ArrayList<String> nonExistTags) {
        HashSet<String> tags = wordBank.get(word).getTags();
        for (String tag : tagList) {
            if (tags.contains(tag)) {
                tags.remove(tag);
                deletedTags.add(tag);
            } else {
                nonExistTags.add(tag);
            }
        }
    }
}
