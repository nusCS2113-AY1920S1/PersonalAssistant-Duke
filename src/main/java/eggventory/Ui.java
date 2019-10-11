package eggventory;

import java.util.Scanner;

/**
 * Manages the UI of Eggventory.
 * Prints intro and exit messages, and the standard newline.
 */

public class Ui {

    private Scanner in;

    public Ui() {
        this.in = new Scanner(System.in);
    }

    /**
     * Prints eggventory introduction message.
     */
    public void printIntro() {
        String logo = "  _      __    __                     __         ____         _   __         __               \n"
                + " | | /| / /__ / /______  __ _  ___   / /____    / __/__ ____ | | / /__ ___  / /____  ______ __\n"
                + " | |/ |/ / -_) / __/ _ \\/  ' \\/ -_) / __/ _ \\  / _// _ `/ _ `/ |/ / -_) _ \\/ __/ _ \\/"
                + " __/ // /\n"
                + " |__/|__/\\__/_/\\__/\\___/_/_/_/\\__/  \\__/\\___/ /___/\\_, /\\_, /|___/\\__/_//_/\\__/\\___/_/"
                + "  \\_, / \n"
                + "                                                  /___//___/                           /___/  \n";

        System.out.print(logo);
        print("Hello! I'm Humpty Dumpty\n" + "What can I do for you?");
    }

    /**
     * Primary function that handles printing to the CLI.
     *
     * @param printString String to print (passed in from external objects accessing UI)
     */
    public void print(String printString) {
        printNewLine();

        String[] linesToPrint = printString.split("\n", 0);
        for (int i = 0; i < linesToPrint.length; i++) {
            System.out.println(addIndent() + linesToPrint[i]);
        }

        printNewLine();
    }

    /**
     * Prints error message to CLI.
     */
    public void printError(Exception e) {
        print("Parser error: \n" + e);
    }

    /**
     * Prints the EggVentory exit message.
     */
    public void printExitMessage() {
        print("Bye! Your stonks are safe with me!");
        System.exit(0);
    }

    public String read() {
        return in.nextLine();
    }

    public static String addIndent() {
        return "        ";
    }

    /**
     * Prints the standard newline.
     */
    private void printNewLine() {
        System.out.println(addIndent() + "____________________________________________________________");
    }
}
