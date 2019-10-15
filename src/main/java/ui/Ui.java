package ui;

import Dictionary.Word;
import Dictionary.WordBank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

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
}

