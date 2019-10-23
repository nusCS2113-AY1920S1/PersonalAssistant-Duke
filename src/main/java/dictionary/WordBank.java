package dictionary;

import exception.NoWordFoundException;
import command.OxfordCall;
import storage.Storage;

import java.util.HashSet;
import java.util.TreeMap;
import java.util.ArrayList;


public class WordBank extends Bank {
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

    /**
     * Searched for the Word object containing the word.
     * @param word the word to be found
     * @return a Word object containing the word and its meaning
     * @throws NoWordFoundException when the wordBank does not contain the word
     */
    public Word getWord(String word) throws NoWordFoundException {
        if (wordBank.containsKey(word)) {
            return wordBank.get(word);
        } else {
            throw new NoWordFoundException(word);
        }
    }

    /**
     * Adds a word to the wordBank.
     * @param word the word to be added to the wordBank
     */
    @Override
    public void addWord(Word word) {
        this.wordBank.put(word.getWordString(), word);
    }

    /**
     * Deletes a word with a specific description.
     * @param word string represents a word to be deleted
     * @throws NoWordFoundException if the word doesn't exist in the word bank
     */
    @Override
    public void deleteWord(Word word) throws NoWordFoundException {
        if (wordBank.containsKey(word)) {
            wordBank.remove(word);
        } else {
            throw new NoWordFoundException(word.getWordString());
        }
    }

    /**
     * Looks up for meaning of a specific word.
     * @param word word to be searched for its meaning
     * @return a string represents meaning of that word
     * @throws NoWordFoundException if the word doesn't exist in the word bank nor Oxford dictionary
     */
    public String searchWordMeaning(String word) throws NoWordFoundException {
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
    public void editWordMeaning(String wordToBeEdited, String newMeaning) throws NoWordFoundException {
        if (wordBank.containsKey(wordToBeEdited)) {
            wordBank.get(wordToBeEdited).editMeaning(newMeaning);
        } else {
            throw new NoWordFoundException(wordToBeEdited);
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
