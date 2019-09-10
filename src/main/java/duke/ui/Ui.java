package duke.ui;

import duke.tasks.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {

    private static final String logo = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";
    private static final String boundary = "    ____________________________________________________________";
    private static final String padding = "     ";

    public Scanner in = new Scanner(System.in);
    public void showWelcome() {
        System.out.println("Hello from\n" + logo);
        System.out.println(boundary);
        System.out.println(padding + "Hello! I'm Duke");
        System.out.println(padding + "What can I do for you?");
        System.out.println(boundary);
        System.out.println();
    }

    public void showLine() {
        System.out.println(boundary);
    }

    void showPadding() {
        System.out.print(padding);
    }

    public void showBye() {
        System.out.println("     " + "Bye. Hope to see you again soon!");
    }

    public void showList(ArrayList<Task> tasks) {
        showPadding();
        System.out.println("Here are the tasks in your list: ");
        for (int i = 1; i <= tasks.size(); i++) {
            Task currentTask = tasks.get(i - 1);
            showPadding();
            System.out.println(i + ". " + currentTask);
        }
    }

    public void showDone(Task currentTask) {
        showPadding();
        System.out.println("Nice! I've marked this task as done:");
        showPadding();
        System.out.println("  " + currentTask);
    }

    public void showAdded(Task currentTask, ArrayList<Task> tasks) {
        System.out.println("     Got it. I've added this task:");
        System.out.println("       " + currentTask);
        System.out.println("     Now you have " + tasks.size() + " tasks in the list.");
    }
    public void showDeleted(Task currentTask, ArrayList<Task> tasks) {
        System.out.println("     Noted. I've removed this task:");
        System.out.println("       " + currentTask);
        System.out.println("     Now you have " + (tasks.size() - 1)  + " tasks in the list.");
    }

    public String readCommand(Scanner in) {
        String input = in.nextLine();
        return input;
    }

    public void showError(String message) {
        System.out.println("     " + message);
    }

    public void showLoadingError() {
        System.out.println("     Failed to load file.");
    }


}