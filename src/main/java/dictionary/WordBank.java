package dictionary;

import exception.NoWordFoundException;
import exception.WordAlreadyExistsException;
import exception.WordBankEmptyException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.SortedMap;
import java.util.TreeMap;

public class WordBank {
    private TreeMap<String, Word> wordBank;

    public WordBank() {
        this.wordBank = new TreeMap<>();
    }

    public TreeMap<String, Word> getWordBank() {
        return wordBank;
    }

    public int getSize() {
        return wordBank.size();
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

    public boolean isEmpty() {
        return wordBank.isEmpty();
    }

    /**
     * Deletes a word with a specific description.
     * @param word string represents a word to be deleted
     * @throws NoWordFoundException if the word doesn't exist in the word bank
     */
    public void deleteWord(Word word) throws NoWordFoundException {
        if (wordBank.containsKey(word.getWordString())) {
            wordBank.remove(word.getWordString());
        } else {
            throw new NoWordFoundException(word.getWordString());
        }
    }

    /**
     * Adds a word to the WordBank.
     * @param word Word object represents the word to be added
     * @throws WordAlreadyExistsException if the word has already exists in the WordBank
     */
    public void addWord(Word word) throws WordAlreadyExistsException {
        if (wordBank.containsKey(word.getWordString())) {
            throw new WordAlreadyExistsException(word.getWordString());
        }
        this.wordBank.put(word.getWordString(), word);
    }

    /**
     * Looks up for meaning of a specific word.
     * @param word word to be searched for its meaning
     * @return a string represents meaning of that word
     * @throws NoWordFoundException if the word doesn't exist in the word bank nor Oxford dictionary
     */
    public String searchWordMeaning(String word) throws WordBankEmptyException, NoWordFoundException {
        word = word.toLowerCase();
        if (wordBank.isEmpty()) {
            throw new WordBankEmptyException();
        } else if (!(wordBank.containsKey(word))) {
            throw new NoWordFoundException(word);
        }
        return wordBank.get(word).getMeaning();
    }

    /**
     * Searches for all words with a few beginning characters.
     * @param word a string represents the beginning substring
     * @return list of words that have that beginning substring
     * @throws NoWordFoundException if no words in the WordBank have that beginning substring
     */
    public ArrayList<String> searchWordWithBegin(String word) throws NoWordFoundException {
        ArrayList<String> arrayList = new ArrayList<>();
        String upperBoundWord = wordBank.ceilingKey(word);
        if (upperBoundWord == null || !upperBoundWord.startsWith(word)) {
            throw new NoWordFoundException(word);
        }
        SortedMap<String, Word> subMap = wordBank.subMap(upperBoundWord, wordBank.lastKey());
        for (String s : subMap.keySet()) {
            if (s.startsWith(word)) {
                arrayList.add(s);
            } else {
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
    public HashSet<String> addWordToSomeTags(String wordToBeAddedTag, ArrayList<String> tags)
            throws NoWordFoundException {
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

    /**
     * Adds a tag to a specific word in word bank.
     * @param wordToAddSynonym word that the tag is set for
     * @param synonyms new tags input by user
     * @return tags lists of that word
     * @throws NoWordFoundException if the word doesn't exist in the word bank
     */
    public HashSet<String> addWordToSomeSynonym(String wordToAddSynonym, ArrayList<String> synonyms) throws NoWordFoundException {
        if (!wordBank.containsKey(wordToAddSynonym)) {
            throw new NoWordFoundException(wordToAddSynonym);
        }
        Word word = wordBank.get(wordToAddSynonym);
        for (String synonym : synonyms) {
            word.addSynonym(synonym);
        }
        return word.getSynonyms();
    }

    /**
     * Deletes tags from a word.
     * @param word string representing the main word
     * @param synonymList list of synonyms to be deleted
     * @param deletedSynonyms list of synonyms we have deleted
     * @param nonExistSynonyms synonyms that doesn't exist in the word
     */
    public void deleteSynonyms(String word, ArrayList<String> synonymList,
                               ArrayList<String> deletedSynonyms, ArrayList<String> nonExistSynonyms) {
        HashSet<String> synonyms = wordBank.get(word).getSynonyms();
        for (String synonym : synonymList) {
            if (synonyms.contains(synonym)) {
                synonyms.remove(synonym); //remove from HashSet
                deletedSynonyms.add(synonym);//keep track of what we have deleted
            } else {
                nonExistSynonyms.add(synonym); //if does not exist in HashSet, we keep track of synonyms not deletable
            }
        }
    }

    /**
     * Checks spelling when user input a non-existing word.
     * A word is considered to be close will must differs in less than its length
     * compared to the searched word.
     * Also consider words with one swap between any 2 characters.
     * @param word word to be searched
     * @return list of words that is considered to be close from the word user is looking for
     */
    public ArrayList<String> getClosedWords(String word) {
        ArrayList<String> closedWords = new ArrayList<>();
        for (Word w : wordBank.values()) {
            boolean isClosed = false;
            if (w.isClosed(word)) {
                isClosed = true;
            }
            for (int i = 0; i < word.length() && !isClosed; i++) {
                for (int j = i + 1; j < word.length() && !isClosed; j++) {
                    StringBuilder wordWithOneSwap = new StringBuilder(word);
                    wordWithOneSwap.setCharAt(i, word.charAt(j));
                    wordWithOneSwap.setCharAt(j, word.charAt(i));
                    if (w.isClosed(wordWithOneSwap.toString())) {
                        isClosed = true;
                    }
                }
            }
            if (isClosed) {
                closedWords.add(w.getWordString());
            }
        }
        return closedWords;
    }

    public void addTagToWord(String word, String tag) {
        wordBank.get(word).addTag(tag);
    }

    public void addSynonymToWord(String word, String synonym) { wordBank.get(word).addSynonym(synonym); }

    public Word[] getAllWordsAsList() {
        return wordBank.values().toArray(new Word[wordBank.size()]);
    }
}
