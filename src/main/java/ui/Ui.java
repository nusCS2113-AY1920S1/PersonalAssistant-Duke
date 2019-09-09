package ui;

import java.util.Scanner;

public class Ui {
    /**
     * Scanner object used for reading input from user.
     */
    private Scanner sc;

    /**
     * Constructs a new ui.Ui object.
     */
    public Ui(){
        sc = new Scanner(System.in);
    }

    /**
     * Prints the welcome message of the program.
     */
    public void welcomeMsg(){
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        printLine();
        System.out.println("Hello! I'm ui.Duke");
        System.out.println("What can I do for you?");
        printLine();
    }

    /**
     * Prints the goodbye message when the user exits the program.
     */
    public void byeMsg(){
        printLine();
        System.out.println("Bye. Hope to see you again soon!");
        printLine();
    }

    /**
     * Prompts the user for input.
     * @return The input of the user.
     */
    public String readLine(){
        return sc.nextLine();
    }

    /**
     * Prints the separator lines for UI
     */
    public void printLine(){
        System.out.println("____________________________________________________________");
    }
}
