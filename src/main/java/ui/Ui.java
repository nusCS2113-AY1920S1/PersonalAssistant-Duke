package ui;

import Dictionary.Word;
import Dictionary.WordBank;
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
        System.out.println(
                "     Hello! I'm" + "      ____        _        \n"
                        +  "                    |  _ \\ _   _| | _____ \n"
                        + "                    | | | | | | | |/ / _ \\\n"
                        + "                    | |_| | |_| |   <  __/\n"
                        + "                    |____/ \\__,_|_|\\_\\___|\n\n"
                        + "     What can I do for you?");
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

    public void showList(WordBank wordBank) {
        System.out.println("     Here are your words:");
        System.out.println(wordBank.getBankData());
    }
}

