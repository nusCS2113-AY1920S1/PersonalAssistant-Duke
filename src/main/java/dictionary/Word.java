package dictionary;


import java.util.HashSet;

/**
 * Represents words stored in the Word Bank.
 */

public class Word {
    private String word;
    private String meaning;
    private HashSet<String> tags;

    /**
     * Maximum ratio of difference allowed for 2 words to be considered close.
     */
    private static final double MAX_DIF_ALLOWED = 0.5;

    /**
     * Number of times that a word is searched.
     */
    private int numberOfSearches;

    /**
     * String represents the closest time that user search for a specific word.
     */
    private String closetSearch;

    /**
     * Initializes a word without tags.
     * @param word description of the word
     * @param meaning meaning of the word
     */
    public Word(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
        this.tags = new HashSet<>();
        this.numberOfSearches = 0;
    }

    /**
     * Initializes a word with tags.
     * @param word description of the word
     * @param meaning meaning of the word
     * @param tags hash set containing tags that are added to word
     */
    public Word(String word, String meaning, HashSet<String> tags) {
        this.word = word;
        this.meaning = meaning;
        this.tags = tags;
        this.numberOfSearches = 0;
    }

    public void setClosetSearch(String closetSearch) {
        this.closetSearch = closetSearch;
    }

    public String getWord() {
        return word;
    }

    public int getNumberOfSearches() {
        return numberOfSearches;
    }

    public void incrementNumberOfSearches() {
        this.numberOfSearches += 1;
    }

    public String getClosetSearch() {
        return closetSearch;
    }

    public String getMeaning() {
        return meaning;
    }

    public HashSet<String> getTags() {
        return tags;
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public void editMeaning(String newMeaning) {
        this.meaning = newMeaning;
    }

    /**
     * Counts the number of different characters with another word.
     * @param another string represents word to be compared
     * @return number of different characters between 2 words
     */
    private double differenceToWord(String another) {
        int lengthOfShorterWord = Math.min(another.length(), word.length());
        int count = 0;
        for (int i = 0; i < lengthOfShorterWord; i++) {
            if (word.charAt(i) != another.charAt(i)) {
                count++;
            }
        }
        return count * 1.0 / lengthOfShorterWord;
    }

    /**
     * Checks if 2 words are closed to each other.
     * @param another string represents word to be compared
     * @return true if 2 words are closed with each other
     */
    public boolean isClosed(String another) {
        return differenceToWord(another) < MAX_DIF_ALLOWED;
    }

    @Override
    public String toString() {
        return word + ": " + meaning;
    }
}
