package dictionary;

import exception.NoTagFoundException;
import exception.NoWordFoundException;
import exception.WordAlreadyExistsException;
import exception.WordBankEmptyException;
import exception.WordCountEmptyException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

public class Bank {
    WordBank wordBank;
    TagBank tagBank;
    SynonymBank synonymBank;
    WordCount wordCount;

    /**
     * Initiates an empty bank.
     */
    public Bank() {
        wordBank = new WordBank();
        tagBank = new TagBank();
        synonymBank = new SynonymBank();
        wordCount = new WordCount(wordBank);
    }

    public WordBank getWordBankObject() {
        return wordBank;
    }

    public TreeMap<String, Word> getWordBankData() {
        return wordBank.getWordBank();
    }

    public WordCount getWordCountObject() {
        return wordCount;
    }

    public Word getWordFromWordBank(String word) throws NoWordFoundException {
        return wordBank.getWord(word);
    }

    public TagBank getTagBank() {
        return tagBank;
    }

    public int getWordBankSize() {
        return wordBank.getSize();
    }

    public int getTagBankSize() {
        return tagBank.getSize();
    }

    /**
     * Adds a word into bank and updates wordBank, tagBank, and wordCount with the new word.
     * @param word Word object represents the added word
     * @throws WordAlreadyExistsException if the word already exists in bank
     */
    public void addWord(Word word) throws WordAlreadyExistsException {
        wordBank.addWord(word);
        tagBank.addWordToAllTags(word);
        wordCount.addWord(word);
    }

    /**
     * Returns true if wordBank is empty.
     * @return boolean value indicating if wordBank is empty
     */
    public boolean wordBankIsEmpty() {
        return wordBank.isEmpty();
    }

    /**
     * Adds word to the wordBank, tagBank and wordCount.
     * @param word word object to be added
     * @throws WordAlreadyExistsException if the word already exists in the bank
     */
    public void addWordToBank(Word word) throws WordAlreadyExistsException {
        wordBank.addWord(word);
        tagBank.addWordToAllTags(word);
        synonymBank.addWordAllSynonyms(word);
        wordCount.addWord(word);
    }

    /**
     * Deletes word from the wordBank, tagBank and wordCount.
     * @param word word object to be deleted
     * @throws NoWordFoundException if the word cannot be found in the bank
     */
    public void deleteWordFromBank(Word word) throws NoWordFoundException {
        wordBank.deleteWord(word);
        tagBank.deleteWordAllTags(word);
        synonymBank.deleteWordAllSynonyms(word);
        wordCount.deleteWord(word);
    }

    /**
     * Adds a list of tags to a word in WordBank and adds the word to all tags.
     * @param wordDescription word to be added tag
     * @param tags list of tags to add
     * @return all tags of the word after adding to show to user
     * @throws NoWordFoundException if the word doesn't exist in the WordBank
     */
    public HashSet<String> addWordToSomeTags(String wordDescription, ArrayList<String> tags)
            throws NoWordFoundException {
        HashSet<String> tagsOfWord = wordBank.addWordToSomeTags(wordDescription, tags);
        tagBank.addWordToSomeTags(wordDescription, tags);
        return tagsOfWord;
    }

    public void deleteTags(String deletedWord, ArrayList<String> tags,
                           ArrayList<String> deletedTags, ArrayList<String> nullTags) {
        wordBank.deleteTags(deletedWord, tags, deletedTags, nullTags);
        tagBank.deleteWordSomeTags(deletedTags, deletedWord);
    }

    /**
     * Adds a list of synonyms to a word in WordBank and adds the word to each of the synonyms.
     * @param wordDescription word to be added tag
     * @param synonyms list of tags to add
     * @return all synonyms of the word after adding to show to user
     * @throws NoWordFoundException if the word doesn't exist in the WordBank
     * @author Ng Jian Wei
     */
    public HashSet<String> addSynonym(String wordDescription, ArrayList<String> synonyms) throws NoWordFoundException {
        HashSet<String> synonymHashSet = wordBank.addSynonym(wordDescription, synonyms);
        synonymBank.addSynonym(wordDescription, synonyms);
        return synonymHashSet;
    }

    public void deleteSynonyms(String deletedWord, ArrayList<String> synonyms,
                               ArrayList<String> deletedSynonyms, ArrayList<String> nullSynonyms) {
        wordBank.deleteSynonyms(deletedWord, synonyms, deletedSynonyms, nullSynonyms);
        synonymBank.deleteWordSomeSynonyms(deletedSynonyms, deletedWord);
    }

    public Word editWordMeaning(String editedWord, String newMeaning) throws NoWordFoundException {
        return wordBank.editWordMeaningAndGetWord(editedWord, newMeaning);
    }

    public ArrayList<String> searchWordWithBegin(String begin) throws NoWordFoundException {
        return wordBank.searchWordWithBegin(begin);
    }

    public String searchWordBankForMeaning(String searchTerm) throws WordBankEmptyException, NoWordFoundException {
        return wordBank.searchWordMeaning(searchTerm);
    }

    public void increaseSearchCount(String searchTerm) throws WordCountEmptyException, NoWordFoundException {
        wordCount.increaseSearchCount(searchTerm, wordBank);
    }

    public ArrayList<String> getClosedWords(String searchTerm) {
        return wordBank.getClosedWords(searchTerm);
    }

    public void addTagToWord(String word, String tag) {
        wordBank.addTagToWord(word, tag);
        tagBank.addWordToOneTag(word, tag);
    }

    public boolean tagBankEmpty() {
        return tagBank.isEmpty();
    }

    /**
     * Gets all words of a specific tag.
     * @param searchTag tag to be searched
     * @return a primitive array of all strings represent words belong to the tag
     * @throws NoTagFoundException if the tag doesn't exist in the tag bank
     */
    public String[] getWordsOfTag(String searchTag) throws NoTagFoundException {
        if (!tagBank.contains(searchTag)) {
            throw new NoTagFoundException(searchTag);
        }
        return tagBank.getAllWordsOfTag(searchTag);
    }

    public String[] getAllTags() {
        return tagBank.getAllTagsAsList();
    }
}
