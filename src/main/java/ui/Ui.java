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

    public String showDeleted(Word w) {
        return "Noted. I've removed this word:\n" + w.toString();
    }

    public String showAdded(Word w) {
        return "Got it. I've added this word:\n" + w.toString();
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


    public void quizDisplay(String question, String[] options, int optionSequence){
        System.out.println("What is the meaning of " + question +"?");
        int index=1;
        for(int i=optionSequence; i<optionSequence+4; i++){
            System.out.print(index + "." + options[i%4] + "  ");
            index++;
        }
        System.out.println();
        return;
    }
    public void quizResponse(Boolean isCorrect, String answer){
        if(isCorrect){
            System.out.println("Yes!! The correct answer is \""+ answer + "\".");
        }
        else{
            System.out.println("Sorry, The answer is \""+ answer + "\".");
        }
        return;
    }
    public void quizWordsNotEnough(){
        System.out.println("    Words not enough. Need at least 4 words to make a quiz!");
        return;

    public void showHistory(Stack<Word> wordHistory, int numberOfWordsToDisplay) {
        int numberOfWords;
        if (numberOfWordsToDisplay > wordHistory.size()) {
            System.out.println("     The number of words requested exceeds the number of words in your word bank.");
            numberOfWords = wordHistory.size();
        } else {
            numberOfWords= numberOfWordsToDisplay;
        }
        System.out.println("     Here are the last " + numberOfWords + " words you have added:");
        for (int i = 0; i < numberOfWords; i++) {
            System.out.println("     " + wordHistory.peek());
            wordHistory.pop();
        }
    }
}

