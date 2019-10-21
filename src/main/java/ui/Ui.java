package ui;

import dictionary.Word;
import dictionary.WordBank;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

/**
 * Represents the object that displays prompts and feedback from the system to the user's commands.
 */
public class Ui {

    /**
     * Greets users when they open the app.
     * @return a greeting string
     */
    public String greet() {
        return ("\n                      |   | _ _ _|   /  \\ _  \n"
                + "                      |/\\|(_)| (_|  \\__/|_) \n"
                + "                                            |   \n\n"
                + "Welcome, what would you like to do today?"
            );
    }

    /**
     * Greets user when they move to quiz scene.
     * @return a greeting string
     */
    public String quizGreet() {
        return ("\n                      |   | _ _ _|   /  \\ _  \n"
                    + "                      |/\\|(_)| (_|  \\__/|_) \n"
                + "                                            |   \n"
                + "Let's do some quiz to enhance your word knowledge \n"
                + "Type \"start\" to begin quiz or \"exit_quiz\" to go back"
            );
    }

    public String showDeleted(Word w) {
        return "Noted. I've removed this word:\n" + w.toString();
    }

    public String showAdded(Word w) {
        return "Got it. I've added this word:\n" + w.toString();
    }

    public String showEdited(Word w) {
        return "Got it. I've edited this word:\n" + w.toString();
    }

    /**
     * Shows the tags to be added.
     * @param word word to add tag
     * @param tags list of tags to be added
     * @param tagList hash set represents existed tags of the word
     * @return a string shown when command is completed
     */
    public String showAddTag(String word, ArrayList<String> tags, HashSet<String> tagList) {
        String returnedString = "I have added " + (tags.size() == 1 ? "this tag \"" + tags.get(0) + "\"" : "these tags")
                + " to word \"" + word + "\"" + "\n";
        returnedString += "Here " + (tagList.size() == 1 ? "is the tag " : "are the tags ")
                + "of word \"" + word + "\"" + "\n";
        StringBuilder stringBuilder = new StringBuilder();
        for (String tag : tagList) {
            stringBuilder.append(tag + "\n");
        }
        return returnedString + stringBuilder.toString();
    }

    public String showAddSyn(String word, ArrayList<String> synonyms, HashSet<String> synonymList) {
        String returnedString = "I have added " + (synonyms.size() == 1 ? "this synonym \"" + synonyms.get(0) + "\"" : "these synonyms")
                + " to word \"" + word + "\"" + "\n";
        returnedString += "Here " + (synonymList.size() == 1 ? "is the synonym " : "are the synonyms ")
                + "of word \"" + word + "\"" + "\n";
        StringBuilder stringBuilder = new StringBuilder();
        for (String syn : synonymList) {
            stringBuilder.append(syn + "\n");
        }
        return returnedString + stringBuilder.toString();
    }

    /**
     * Shows the list of all words in the word bank.
     * @param wordBank to store all words
     * @param order order to show words (ascending / descending)
     * @return a string shown when command is completed
     */
    public String showList(WordBank wordBank, String order) {
        String returnedString = "Here are your words:\n";
        if (order.equals("asc") || order.equals("")) {
            for (Map.Entry<String, Word> entry : wordBank.getWordBank().entrySet()) {
                returnedString += entry.getValue() + "\n";
            }
        } else {
            for (String description : wordBank.getWordBank().descendingKeySet()) {
                returnedString += wordBank.getWordBank().get(description) + "\n";
            }
        }
        return returnedString;
    }

    /**
     * Shows completion when tags are deleted from words.
     * @param word word to be deleted tags
     * @param deletedTags list containing tags to delete from word
     * @return a string shown when command is completed
     */
    public String showDeletedTags(String word, ArrayList<String> deletedTags) {
        StringBuilder stringBuilder = new StringBuilder();
        if (deletedTags.size() > 0) {
            stringBuilder.append("I have removed " + (deletedTags.size() == 1 ? "this tag " : "these tags ")
                    + "from the word \"" + word + "\"" + "\n");
            for (String tag : deletedTags) {
                stringBuilder.append(tag + "\n");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Shows non-existing tags of the words that are searched.
     * @param word a string represents a word to be searched for tags
     * @param nullTags list of non-existing tags that are searched
     * @return a string to show all non-existing tags
     */
    public String showNullTags(String word, ArrayList<String> nullTags) {
        StringBuilder stringBuilder = new StringBuilder();
        if (nullTags.size() > 0) {
            stringBuilder.append((nullTags.size() == 1 ? "This tag " : "These tags ")
                    + "doesn't exist in the word \"" + word + "\"" + "\n");
            for (String tag : nullTags) {
                stringBuilder.append(tag + "\n");
            }
        }
        return stringBuilder.toString();
    }

    public String showSearch(String description, String meaning) {
        return ("Here is the meaning of " + description + ": " + meaning);
    }

    /**
     * Shows a string to inform the completion when user look for search history.
     * @param wordHistory stack containing the closest searches
     * @param numberOfWordsToDisplay number of closest searched words to display
     * @return a string shown when command is completed
     */
    public String showHistory(Stack<Word> wordHistory, int numberOfWordsToDisplay) {
        int numberOfWords;
        String s = "";
        if (numberOfWordsToDisplay > wordHistory.size()) {
            s += "The number of words requested exceeds the number of words in your word bank.\n";
            numberOfWords = wordHistory.size();
        } else {
            numberOfWords = numberOfWordsToDisplay;
        }
        s += ("Here are the last " + numberOfWords + " words you have added:\n");
        for (int i = 0; i < numberOfWords; i++) {
            s += wordHistory.peek() + "\n";
            wordHistory.pop();
        }
        return s;
    }

    /**
     * Displays quiz to ask user.
     * @param question word to be asked for meaning
     * @param options options available to be chosen
     * @param optionSequence sequence of the options
     * @return a string shown when the command is completed
     */
    public String quizDisplay(String question, String[] options, int optionSequence) {
        String s = ("What is the meaning of " + question + "?\n");
        int index = 1;
        for (int i = optionSequence; i < optionSequence + 4; i++) {
            s += (index + "." + options[i % 4] + "  ");
            index++;
        }
        s += "\n";
        return s;
    }

    /**
     * Shows respond of bot when user input the answer.
     * @param isCorrect is true if user get the correct answer
     * @param answer correct answer
     * @return a string shown when the command is completed
     */
    public String quizResponse(Boolean isCorrect, String answer) {
        if (isCorrect) {
            return ("Yes!! The correct answer is \"" + answer + "\".");
        } else {
            return ("Sorry, The answer is \"" + answer + "\".");
        }
    }
}

