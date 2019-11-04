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

    public String getWordString() {
        return word;
    }

    public void setStringLowerCase() {
        word = word.toLowerCase();
    }

    public int getNumberOfSearches() {
        return numberOfSearches;
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

    public void incrementNumberOfSearches() {
        this.numberOfSearches += 1;
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
        int count = editDistance(another);
        return count * 1.0 / lengthOfShorterWord;
    }

    /**
     * Levenshtein distance between 2 strings.
     * Calculates the minimum number of changes from 1 string to get to another
     * Possible changes are insert, delete or change change characters
     * @param another represents another string to be compared
     * @return the minimum number of edits
     */
    private int editDistance (String another) {
        int m = word.length();
        int n = another.length();
        int[][] edit = new int[m][n];
        for (int i = 0; i < n; i++) {
            edit[0][i] = i;
        }
        for (int i = 1; i < m; i++) {
            edit[i][0] = i;
            for (int j = 1; j < n; j++) {
                int replace;
                if (word.charAt(i) == another.charAt(j)) {
                    replace = edit[i-1][j-1];
                } else {
                    replace = edit[i-1][j-1] + 1;
                }
                edit[i][j] = Math.min(replace, Math.min(edit[i][j-1] + 1, edit[i-1][j] + 1));
            }
        }
        return edit[m-1][n-1];
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