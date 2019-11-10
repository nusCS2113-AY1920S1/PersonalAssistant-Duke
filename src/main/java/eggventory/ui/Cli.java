package eggventory.ui;

import java.util.Scanner;

//@@author Raghav-B
/**
 * Manages the CLI of Eggventory.
 * Prints intro and exit messages, and the standard newline.
 */
public class Cli extends Ui {

    private Scanner in;

    public Cli() {
        this.in = new Scanner(System.in);
    }

    /**
     * Starts the REPL loop.
     * @param runMethod Function passed in for REPL loop.
     */
    public void initialize(Runnable runMethod) {
        printIntro();

        while (true) {
            runMethod.run();
        }
    }

    /**
     * Reads input from stdio.
     * @return Returns String to be used by Parser in REPL loop.
     */
    public String read() {
        return in.nextLine();
    }

    /**
     * Primary function that handles printing to the CLI.
     *
     * @param printString String to print (passed in from external objects accessing UI)
     */
    public String print(String printString) {
        String output = printFormatter(printString);
        System.out.print(output);

        return output;
    }
}
