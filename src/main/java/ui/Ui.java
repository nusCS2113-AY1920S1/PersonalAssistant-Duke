package ui;

import Dictionary.Word;
import Dictionary.WordBank;
import exception.NoWordFoundException;

import java.util.ArrayList;
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

    public void showLine() {
        System.out.println("    ____________________________________________________________");
    }

    public void greet() {
        showLine();
        System.out.println("\n                      |  | _  _ _|  /  \\ _  \n"
                        + "                      |/\\|(_)| (_|  \\__/|_) \n"
                        + "                                        |   \n\n"
                        + "             Welcome, what would you like to do today?"
        );
        showLine();
    }

    public void goodBye() {
        System.out.println("     Bye. Hope to see you again soon!");
    }

    public void showDeleted(Word w) {
        System.out.println("     Noted. I've removed this word:");
        System.out.println("       " + w.toString());
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

    public void showAdded(Word w) {
        System.out.println("     Got it. I've added this word:");
        System.out.println("       " + w.toString());
    }

    public void showList(WordBank wordBank, String order) {
        System.out.println("     Here are your words:");
        if (order.equals("asc") || order.equals("")) {
            for (Map.Entry<String, Word> entry : wordBank.getWordBank().entrySet()) {
                System.out.println("     " + entry.getValue());
            }
        }
        else {
            for (String description : wordBank.getWordBank().descendingKeySet()) {
                System.out.println("     " + wordBank.getWordBank().get(description));
            }
        }
    }

    public void showSearch(String description, String meaning){
        System.out.println("     Here is the meaning of " + description + ": " + meaning);
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
    }
}

