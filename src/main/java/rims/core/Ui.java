package rims.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//@@author rabhijit
/**
 * Handles the taking in of input from the user and passes it to the Parser to
 * translate it into usable commands. Also handles printing of messages for the
 * user's reading.
 */
public class Ui {
    protected Scanner inputScanner;
    protected String input;
    protected int intInput;
    protected String arrow = ">> ";
    protected String line = "______________________________________________"
            + "______________________________________________________________________________________________";
    protected String dash = "- - - - - - - - - - - - - - - - - - - - - - - - - "
            + "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ";
    protected String hash = "***************************************************************"
            + "*****************************************************************************";
    protected String tab = "\t";
    protected ArrayList<String> welcomeMsg = new ArrayList<String>(Arrays.asList("Welcome to RIMS, your Resource"
        + " & Inventory Management System.",
        "How can I help you?",
        "\n",
        "Type 'help' to display a list of all commands supported by RIMS."));
    protected ArrayList<String> commands = new ArrayList<String>(
            Arrays.asList("COMMANDS CURRENTLY SUPPORTED BY RIMS:\n",
            "add - add a new resource to inventory",
            "delete - delete an existing resource from inventory",
            "loan - loan out an item from now till your desired future date",
            "reserve - reserve an item between two future dates",
            "return - return a loan or reservation",
            "list - see all resources and current reservations",
            "\t" + "list /item - see all loans and future reservations of a particular item",
            "\t" + "list /room - see all loans and future reservations of a particular room",
            "\t" + "list /date - see all resources available on a particular date",
            "deadlines - view all currently active loans and reservations",
            "stats - view loan and reservation statistics",
            "calendar - view a monthly calendar showing all booked resources",
            "\t" + "calendar+ - enlarge the cell size of the calendar",
            "\t" + "calendar- - reduce the cell size of the calendar",
            "undo - undo the last command that modified inventory data"));

    public Ui() {
        inputScanner = new Scanner(System.in);
        welcome();
    }

    /**
     * Obtains a new String input from the user.
     * 
     * @return the new input typed by the user.
     */
    public String getInput() {
        input = inputScanner.nextLine();
        System.out.println(arrow + input);
        return input;
    }

    // @@author isbobby
    /**
     * Prints a question for the user, before obtaining a new String input in
     * response from the user.
     * 
     * @return the new input typed by the user.
     */
    public String getInput(String question) {
        formattedPrint(question);
        input = inputScanner.nextLine();
        System.out.println(arrow + input);
        return input;
    }

    /**
     * Obtains a new integer input from the user, without requiring conversion from
     * String to integer format.
     * 
     * @return the new integer input typed by the user.
     */
    public int getIntegerInput() {
        intInput = inputScanner.nextInt();
        System.out.println(arrow + intInput);
        return intInput;
    }

    /**
     * Prints a question for the user, before obtaining a new integer input in
     * response from the user.
     * 
     * @return the new integer input typed by the user.
     */
    public int getIntegerInput(String question) {
        formattedPrint(question);
        intInput = inputScanner.nextInt();
        System.out.println(arrow + intInput);
        return intInput;
    }

    // @@author rabhijit
    /**
     * Prints a line of underscores.
     */
    public void printLine() {
        System.out.println(tab + line);
    }

    // @@author isbobby
    /**
     * Prints a line of dashes.
     */
    public void printDash() {
        System.out.println(tab + dash);
    }

    // @@author rabhijit
    /**
     * Prints a desired line for the user to read.
     */
    public void print(String input) {
        System.out.println(tab + input);
    }

    /**
     * Prints an array of lines.
     */
    public void printArray(ArrayList<String> inputs) {
        for (int i = 0; i < inputs.size(); i++) {
            System.out.println("\t" + inputs.get(i));
        }
    }

    /**
     * Prints an empty line.
     */
    public void printEmptyLine() {
        System.out.println();
    }

    /**
     * Prints an array of lines in the standard RIMS format, bordered by lines.
     */
    public void formattedPrintArray(ArrayList<String> inputs) {
        printLine();
        printArray(inputs);
        printLine();
    }

    /**
     * Prints a line in the standard RIMS format, bordered by lines.
     */
    public void formattedPrint(String input) {
        printLine();
        print(input);
        printLine();
    }

    /**
     * Prints the farewell message when RIMS is closed.
     */
    public void farewell() {
        formattedPrint("Bye. Hope to see you again soon!");
    }

    /**
     * Prints a welcome message when RIMS is started up.
     */
    public void welcome() {
        printLogo();
        formattedPrintArray(welcomeMsg);
    }

    /**
     * Prints a list of all valid RIMS commands.
     */
    public void help() {
        formattedPrintArray(commands);
    }

    // @@author danielcyc
    /**
     * Prints the RIMS logo, as part of the RIMS welcome message.
     */
    public void printLogo() {
        String logo = "\n" 
                +
                tab + "          _____                    _____"
                + "                    _____                    _____          \n"
                +
                tab + "         /\\    \\                  /\\    \\"
                + "                  /\\    \\                  /\\    \\         \n"
                +
                tab + "        /::\\    \\                /::\\    \\"
                + "                /::\\____\\                /::\\    \\        \n"
                +
                tab + "       /::::\\    \\               \\:::\\    "
                + "\\              /::::|   |               /::::\\    \\       \n"
                +
                tab + "      /::::::\\    \\               \\:::\\    \\        "
                + "    /:::::|   |              /::::::\\    \\      \n"
                +
                tab + "     /:::/\\:::\\    \\               \\:::\\    "
                + "\\          /::::::|   |             /:::/\\:::\\    \\     \n"
                +
                tab + "    /:::/__\\:::\\    \\               \\:::\\    "
                + "\\        /:::/|::|   |            /:::/__\\:::\\    \\    \n"
                +
                tab + "   /::::\\   \\:::\\    \\              /::::\\    "
                + "\\      /:::/ |::|   |            \\:::\\   \\:::\\    \\   \n"
                +
                tab + "  /::::::\\   \\:::\\    \\    ____    /::::::\\    "
                + "\\    /:::/  |::|___|______    ___\\:::\\   \\:::\\    \\  \n"
                +
                tab + " /:::/\\:::\\   \\:::\\____\\  /\\   \\  /:::/\\:::"
                + "\\    \\  /:::/   |::::::::\\    \\  /\\   \\:::\\   \\:::\\    \\ \n"
                +
                tab + "/:::/  \\:::\\   \\:::|    |/::\\   \\/:::/  "
                + "\\:::\\____\\/:::/    |:::::::::\\____\\/::\\   \\:::\\   \\:::\\____\\\n"
                +
                tab + "\\::/   |::::\\  /:::|____|\\:::\\  /:::/    "
                + "\\::/    /\\::/    / ~~~~~/:::/    /\\:::\\   \\:::\\   \\::/    /\n"
                +
                tab + " \\/____|:::::\\/:::/    /  \\:::\\/:::/    "
                + "/ \\/____/  \\/____/      /:::/    /  \\:::\\   \\:::\\"
                + "   \\/____/ \n"
                +
                tab + "       |:::::::::/    /    \\::::::/    "
                + "/                       /:::/    /    \\:::\\   \\:::\\    \\     \n"
                +
                tab + "       |::|\\::::/    /      \\::::/____/"
                + "                       /:::/    /      \\:::\\   \\:::\\____\\    \n"
                +
                tab + "       |::| \\::/____/        \\:::\\    \\"
                + "                      /:::/    /        \\:::\\  /:::/    /    \n"
                +
                tab + "       |::|  ~|               \\:::\\    \\"
                + "                    /:::/    /          \\:::\\/:::/    /     \n"
                +
                tab + "       |::|   |                \\:::\\    \\"
                + "                  /:::/    /            \\::::::/    /      \n"
                +
                tab + "       \\::|   |                 \\:::\\____\\"
                + "                /:::/    /              \\::::/    /       \n"
                +
                tab + "        \\:|   |                  \\::/    /   "
                + "             \\::/    /                \\::/    /        \n"
                +
                tab + "         \\|___|                   \\/____/"
                + "                  \\/____/                  \\/____/         \n"
                +
                tab + "                                                         "
                + "                                           \n";
        System.out.println(logo);
    }

}