package dictionary;


import java.util.HashSet;

/**
 * Represents words stored in the Word Bank.
 */

public class Word {
    private String word;
    private String meaning;
    private HashSet<String> tags;
    private HashSet<String> synonyms;

    /**
     * Maximum ratio of difference allowed for 2 words to be considered close.
     */
    private static final double MAX_DIF_ALLOWED = 0.6;

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
        this.synonyms = new HashSet<>();
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
        this.synonyms = new HashSet<>();
        this.numberOfSearches = 0;
    }

    public String getWordString() {
        return word;
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

    public HashSet<String> getSynonyms() {
        return synonyms;
    }

    public void addSynonym(String synonym) {
        this.synonyms.add(synonym);
    }

    public void incrementNumberOfSearches() {
        this.numberOfSearches += 1;
    }

    public void editMeaning(String newMeaning) {
        this.meaning = newMeaning;
    }

    /**
     * Levenshtein distance between 2 strings.
     * Calculates the minimum number of changes from 1 string to get to another
     * Possible changes are insert, delete or change change characters
     * @param another string represents word to be compared
     * @return edit distance between 2 words divided by the length of shorter word
     */
    private double differenceToWord(String another) {
        int[][] dp = new int[word.length() + 1][another.length() + 1];

        for (int i = 0; i <= word.length(); i++) {
            for (int j = 0; j <= another.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = dp[i - 1][j - 1] + ((word.charAt(i - 1) != another.charAt(j - 1)) ? 1 : 0);
                    if (dp[i][j] > dp[i - 1][j] + 1) {
                        dp[i][j] = dp[i - 1][j] + 1;
                    } else if (dp[i][j] > dp[i][j - 1] + 1) {
                        dp[i][j] = dp[i][j - 1] + 1;
                    }
                }
            }
        }
        int lengthOfShorterWord = Math.min(another.length(), word.length());
        return dp[word.length()][another.length()] * 1.0 / lengthOfShorterWord;
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