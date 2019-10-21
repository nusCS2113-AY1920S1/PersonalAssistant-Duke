package dictionary;public class WordCount{private final dictionary.WordBank wordBank;/**
     * Maps the search count (KEY) to an ordered list of words (VALUE) with that search count.
     */
    private java.util.TreeMap<java.lang.Integer,java.util.TreeMap<java.lang.String,dictionary.Word>> wordCount = new java.util.TreeMap<java.lang.Integer,java.util.TreeMap<java.lang.String,dictionary.Word>>();	public WordCount(dictionary.WordBank wordBank)	{		this.wordBank = wordBank;	}public java.util.TreeMap<java.lang.Integer,java.util.TreeMap<java.lang.String,dictionary.Word>> getWordCount() {
        return wordCount;
    }protected void makeWordCount() {
        for (java.util.Map.Entry<java.lang.String,dictionary.Word> entry : wordBank.getWordBank().entrySet()) {
            //find key. if exists append to treemap, else create new hashmap entry
            int numberOfSearches = entry.getValue().getNumberOfSearches();
            java.lang.String wordText = entry.getValue().getWord();
            dictionary.Word wordWord = entry.getValue();
            if (!wordCount.isEmpty()) {
                if (wordCount.containsKey(numberOfSearches)) {
                    wordCount.get(numberOfSearches).put(wordText, wordWord);
                }
            } else {
                wordCount.put(numberOfSearches, new java.util.TreeMap<java.lang.String,dictionary.Word>());
                wordCount.get(numberOfSearches).put(wordText, wordWord);
            }
        }
    }/**
     * Increases the search count for a word.
     * @param searchTerm word that is being searched for by the user
     * @throws exception.NoWordFoundException if word does not exist in the word bank
     */
    public void increaseSearchCount(java.lang.String searchTerm) throws exception.NoWordFoundException {
        if (wordBank.getWordBank().containsKey(searchTerm)) {
            dictionary.Word searchedWord = wordBank.getWordBank().get(searchTerm);
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
                wordCount.put(newSearchCount, new java.util.TreeMap<java.lang.String,dictionary.Word>());
                wordCount.get(newSearchCount).put(searchTerm, searchedWord); //create new entry and add word to treemap
            }
        } else {
            throw new exception.NoWordFoundException(searchTerm);
        }
    }}