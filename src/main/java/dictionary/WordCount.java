package dictionary;

import exception.NoWordFoundException;

import java.util.Map;
import java.util.TreeMap;

/**
 * Represents an object that keeps track of operations related to the number of searches on the words in the wordBank.
 */
public class WordCount {

    /**
     * Maps the search count (KEY) to an ordered list of words (VALUE) with that search count.
     */
    protected TreeMap<Integer, TreeMap<String, Word>> wordCount = new TreeMap<Integer, TreeMap<String, Word>>();

    public WordCount(WordBank wordBank) {
        makeWordCount(wordBank);
    }

    public TreeMap<Integer, TreeMap<String, Word>> getWordCount() {
        return wordCount;
    }

    /**
     * Instantiates the wordCount with words from the wordBank.
     * @param wordBank contains the list of words to add to wordCount
     */
    protected void makeWordCount(WordBank wordBank) {
        for (Map.Entry<String, Word> entry : wordBank.getWordBank().entrySet()) {
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
    public void increaseSearchCount(String searchTerm, WordBank wordBank) throws NoWordFoundException {
        if (wordBank.getWordBank().containsKey(searchTerm)) {
            Word searchedWord = wordBank.getWordBank().get(searchTerm);
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
                wordCount.put(newSearchCount, new TreeMap<String, Word>());
                wordCount.get(newSearchCount).put(searchTerm, searchedWord); //create new entry and add word to treemap
            }
        } else {
            throw new NoWordFoundException(searchTerm);
        }
    }
}