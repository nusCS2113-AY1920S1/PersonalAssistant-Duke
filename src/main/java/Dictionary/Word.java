package Dictionary;

/**
 * Represents words stored in the Word Bank
 */

public class Word {
    private String word;
    private String meaning;
    private String tag;

    /**
     * Number of times that a word is searched
     */
    private int numberOfSearches;

    /**
     * String represents the closest time that user search
     * for a specific word
     */
    private String closetSearch;

    public Word(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
        this.numberOfSearches = 1;
    }

    public Word(String word, String meaning, String tag) {
        this.word = word;
        this.meaning = meaning;
        this.tag = tag;
        this.numberOfSearches = 1;
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

    public String getClosetSearch() {
        return closetSearch;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return word + ": " + meaning;
    }
}
