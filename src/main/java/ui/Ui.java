package ui;

import Dictionary.Word;
import Dictionary.WordBank;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

/**
 * Represents the object that displays prompts and feedback from the system to the user's commands.
 */
public class Ui {

    public String readCommand() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    public String greet() {
        return ("\n                      |   | _ _ _|   /  \\ _  \n"
                + "                      |/\\|(_)| (_|  \\__/|_) \n"
                + "                                            |   \n\n"
                + "Welcome, what would you like to do today?"
        );
    }

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

    public String showAddTag(String word, ArrayList<String> tags, HashSet<String> tagList) {
        String returnedString = "I have added " + (tags.size() == 1 ? "this tag \"" + tags.get(0) + "\"" : "these tags")
                + " to word \"" + word + "\"" + "\n";
        returnedString += "Here " + (tagList.size() == 1 ? "is the tag " : "are the tags ") + "of word \"" + word + "\"" + "\n";
        StringBuilder stringBuilder = new StringBuilder();
        for (String tag : tagList) {
             stringBuilder.append(tag + "\n");
        }
        return returnedString + stringBuilder.toString();
    }

    public String showList(WordBank wordBank, String order) {
        String returnedString = "Here are your words:\n";
        if (order.equals("asc") || order.equals("")) {
            for (Map.Entry<String, Word> entry : wordBank.getWordBank().entrySet()) {
                returnedString += entry.getValue() + "\n";
            }
        }
        else {
            for (String description : wordBank.getWordBank().descendingKeySet()) {
                returnedString += wordBank.getWordBank().get(description) + "\n";
            }
        }
        return returnedString;
    }

    public String showDeletedTags(String word, ArrayList<String> deletedTags) {
        StringBuilder stringBuilder = new StringBuilder();
        if (deletedTags.size() > 0) {
            stringBuilder.append("I have removed " + (deletedTags.size() == 1 ? "this tag " : "these tags ") +
                    "from the word \"" + word + "\"" + "\n");
            for (String tag : deletedTags) {
                stringBuilder.append(tag + "\n");
            }
        }
        return stringBuilder.toString();
    }

    public String showNullTags(String word, ArrayList<String> nullTags) {
        StringBuilder stringBuilder = new StringBuilder();
        if (nullTags.size() > 0) {
            stringBuilder.append((nullTags.size() == 1 ? "This tag " : "These tags ") +
                    "doesn't exist in the word \"" + word + "\"" + "\n");
            for (String tag : nullTags) {
                stringBuilder.append(tag + "\n");
            }
        }
        return stringBuilder.toString();
    }

    public String showSearch(String description, String meaning){
        return ("Here is the meaning of " + description + ": " + meaning);
    }

    public String showSearchFrequency(WordBank wordBank, String order) {
        String returnedString = "You have searched for these words ";
        if (order.equals("asc") || order.equals("")) {
            returnedString += "least:\n";
            for (Map.Entry<String, Word> entry : wordBank.getWordBank().entrySet()) {
                returnedString += entry.getValue().getWord() + " - " + entry.getValue().getNumberOfSearches() + "\n";
            }
        }
        else {
            returnedString += "most:\n";
            for (String word : wordBank.getWordBank().descendingKeySet()) {
                returnedString += wordBank.getWordBank().get(word).getWord() + " - " + wordBank.getWordBank().get(word).getNumberOfSearches() + "\n";
            }
        }
        return returnedString;
    }

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

    public String quizDisplay(String question, String[] options, int optionSequence){
        String s = ("What is the meaning of " + question +"?\n");
        int index=1;
        for(int i=optionSequence; i<optionSequence+4; i++){
            s += (index + "." + options[i%4] + "  ");
            index++;
        }
        s += "\n";
        return s;
    }
    public String quizResponse(Boolean isCorrect, String answer){
        if(isCorrect){
            return ("Yes!! The correct answer is \""+ answer + "\".");
        }
        else{
            return ("Sorry, The answer is \""+ answer + "\".");
        }
    }
}

