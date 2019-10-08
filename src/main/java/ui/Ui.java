package ui;

import Dictionary.Word;
import Dictionary.WordBank;
import exception.NoWordFoundException;

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
        return "Hello! I'm" + "      ____        _        \n"
                +  "                    |  _ \\ _   _| | _____ \n"
                + "                    | | | | | | | |/ / _ \\\n"
                + "                    | |_| | |_| |   <  __/\n"
                + "                    |____/ \\__,_|_|\\_\\___|\n\n"
                + "     What can I do for you?";
    }


    public String showDeleted(Word w) {
        return "Noted. I've removed this word:\n" + w.toString();
    }

    /*
    public void showFound(ArrayList<Task> foundItems) {
        if (foundItems.size() > 0) {
            System.out.println("     Here are the matching tasks in your list:");
            int count = 0;
            for (Task task : foundItems) {
                count++;
                System.out.println("     " + count + ". " + task.toString());
            }
        } else {
            System.out.println("     No task matching description. Try another keyword!");
        }
    }
    */

    public String showAdded(Word w) {
        return "Got it. I've added this word:\n" + w.toString();
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

    public String showSearch(String description, String meaning){
        return "Here is the meaning of " + description + ": " + meaning;
    }
}

