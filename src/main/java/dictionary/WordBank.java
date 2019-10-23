package dictionary;

import java.util.Map;
import java.util.ArrayList;
import java.util.SortedMap;

import exception.NoWordFoundException;
import command.OxfordCall;
import exception.WordAlreadyExistException;
import storage.Storage;

import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;


public class WordBank {
    private TreeMap<String, Word> wordBank;

    /**
     * Maps the search count (KEY) to an ordered list of words (VALUE) with that search count.
     */
    private TreeMap<Integer, TreeMap<String, Word>> wordCount = new TreeMap<>();

    public WordBank(Storage storage) {
        wordBank = storage.loadFile();
        makeWordCount();
    }

    public WordBank(TreeMap<String, Word> wordBank) {
        this.wordBank = wordBank;
    }

    public TreeMap<String, Word> getWordBank() {
        return wordBank;
    }

    public TreeMap<Integer, TreeMap<String, Word>> getWordCount() {
        return wordCount;
    }

    protected void makeWordCount() {
        for (Map.Entry<String, Word> entry : wordBank.entrySet()) {
            //find key. if exists append to treemap, else create new hashmap entry
            int numberOfSearches = entry.getValue().getNumberOfSearches();
            String wordText = entry.getValue().getWord();
            Word wordWord = entry.getValue();
            if (!wordCount.isEmpty()) {
                if (wordCount.containsKey(numberOfSearches)) {
                    wordCount.get(numberOfSearches).put(wordText, wordWord);
                }
            } else {
                wordCount.put(numberOfSearches, new TreeMap<>());
                wordCount.get(numberOfSearches).put(wordText, wordWord);
            }
        }
    }

    /**
     * Increases the search count for a word.
     * @param searchTerm word that is being searched for by the user
     * @throws NoWordFoundException if word does not exist in the word bank
     */
    public void increaseSearchCount(String searchTerm) throws NoWordFoundException {
        if (wordBank.containsKey(searchTerm)) {
            Word searchedWord = wordBank.get(searchTerm);
            int searchCount = searchedWord.getNumberOfSearches();
            searchedWord.incrementNumberOfSearches();
            wordCount.get(searchCount).remove(searchTerm);
            if (wordCount.get(searchCount).isEmpty()) { //treemap is empty, delete key
                wordCount.remove(searchCount);
            }
            int newSearchCount = searchCount + 1;
            if (wordCount.containsKey(newSearchCount)) {
                wordCount.get(newSearchCount).put(searchTerm, searchedWord); //add directly to existing treemap
            } else {
                wordCount.put(newSearchCount, new TreeMap<>());
                wordCount.get(newSearchCount).put(searchTerm, searchedWord); //create new entry and add word to treemap
            }
        } else {
            throw new NoWordFoundException(searchTerm);
        }
    }

    public void addWord(Word word) throws WordAlreadyExistException {
        if (wordBank.containsKey(word.getWord())) {
            throw new WordAlreadyExistException(word.getWord());
        }
        this.wordBank.put(word.getWord(), word);
    }

    /**
     * Looks up for meaning of a specific word.
     * @param word word to be searched for its meaning
     * @return a string represents meaning of that word
     * @throws NoWordFoundException if the word doesn't exist in the word bank nor Oxford dictionary
     */
    public String searchForMeaning(String word) throws NoWordFoundException {
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

    public ArrayList<String> searchWordWithBegin(String word) throws NoWordFoundException {
        word = word.toLowerCase();
        ArrayList<String> arrayList = new ArrayList<>();
        String upperBoundWord = wordBank.ceilingKey(word);
        if (!upperBoundWord.startsWith(word)) {
            throw new NoWordFoundException(word);
        }
        SortedMap<String, Word> subMap = wordBank.subMap(upperBoundWord, wordBank.lastKey());
        for (String s : subMap.keySet()) {
            if (s.startsWith(word)) {
                arrayList.add(s);
            }
            else {
                break;
            }
        }
        return arrayList;
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

    public ArrayList<String> getClosedWords(String word) {
        ArrayList<String> closedWords = new ArrayList<>();
        for (Word w : wordBank.values()) {
            if (w.isClosed(word)) {
                closedWords.add(w.getWord());
            }
        }
        return closedWords;
    }
}
