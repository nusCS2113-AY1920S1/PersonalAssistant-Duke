package moomoo.feature;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Represents the User Interface to be shown to the user.
 */
public class Ui {
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final int DEFAULT_BOX = 45;
    private static String output = null;
    private static String testOutput = null;

    /**
     * Prints out a message.
     * @param text message to be printed
     */
    private static void print(String text) {
        System.out.println(text);
    }

    /**
     * Returns the value to be printed to the GUI.
     * @return String to be printed on the GUI
     */
    public static String returnResponse() {
        return output;
    }

    /**
     * Sets the myOutput to be printed.
     * @param myOutput Input value to be printed.
     */
    public static void setOutput(String myOutput) {
        output = myOutput;
    }

    /**
     * Clears the screen.
     * @param shouldClearScreen if true, clear the screen, else don't.
     */
    public static void clearScreen(boolean shouldClearScreen) {
        if (shouldClearScreen) {
            System.out.print("\u001b[2J");
            System.out.flush();
        }
    }

    /**
     * Prints the welcome message to the User.
     */
    public static void showWelcome() {
        print(" __      _____ _    ___ ___  __  __ ___   _____ ___ \n"
                + " \\ \\    / / __| |  / __/ _ \\|  \\/  | __| |_   _/ _ \\\n"
                + "  \\ \\/\\/ /| _|| |_| (_| (_) | |\\/| | _|    | || (_) |\n"
                + "   \\_/\\_/ |___|____\\___\\___/|_|  |_|___|   |_| \\___/ \n"
                + "\n"
                + " __  __  ___   ___  __  __  ___   ___  __  __  ___  _  _ _____   __\n"
                + "|  \\/  |/ _ \\ / _ \\|  \\/  |/ _ \\ / _ \\|  \\/  |/ _ \\| \\| | __\\ \\ / /\n"
                + "| |\\/| | (_) | (_) | |\\/| | (_) | (_) | |\\/| | (_) | .` | _| \\ V /\n"
                + "|_|  |_|\\___/ \\___/|_|  |_|\\___/ \\___/|_|  |_|\\___/|_|\\_|___| |_|\n");
    }
    

    /**
     * Used to read input from the user.
     * @return String representing the input given by the User
     */
    public static String readCommand() {
        Scanner inputScanner = new Scanner(System.in);
        return inputScanner.nextLine().trim();
    }

    /**
     * Sets good bye message to be shown to the User.
     */
    public static void showGoodbye() {
        output = "Hope you had a great time using MooMooMoney!\n"
                + "See you next time :)";
    }

    /**
     * Returns message of MooMooException that occurs.
     * @param e MooMooException that occurs
     * @return Message of the MooMooException
     */
    public static String printException(MooMooException e) {
        if (!e.getMessage().isBlank()) {
            output = ANSI_RED + e.getMessage() + ANSI_RESET;
        }
        return output;
    }

    /**
     * Prints out response from command.
     */
    public static void showResponse() {
        try {
            PrintStream out = new PrintStream(System.out, true, "UTF-8");
            out.println(output);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        output = "";
    }

    /**
     * Show today's date.
     */
    public static String showDate() {
        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
        String shortDate = today.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        print("\n" + formattedDate + "\n");
        return shortDate;
    }

    /**
     * Prints out when a new category is created.
     * @param categoryName name of the new category
     */
    public static void showCategoryMessage(String categoryName) {
        int boxLength = Math.max(DEFAULT_BOX, categoryName.length());
        boxLength += 5;

        String box = " ";
        box = addChars(boxLength, box, "_");
        box = box.concat("\n/ Mooo.");

        int blanks1 = boxLength - 6;
        box = addChars(blanks1, box, " ");
        box = box.concat("\\\n\\ " + categoryName);

        getBoxBottom(categoryName, boxLength, box, "/\n ");
    }

    /**
     * Prints out when a new expenditure is created.
     * @param expenditureName name of the new expenditure
     * @param categoryName category containing expenditure
     */
    public static void showExpenditureMessage(String expenditureName, String categoryName) {
        int boxLength = Math.max(expenditureName.length(), categoryName.length());
        boxLength = Math.max(boxLength, DEFAULT_BOX);
        boxLength += 5;

        String box = "  ";
        box = addChars(boxLength, box, "_");
        box = box.concat("\n / Mooo.");

        int blanks1 = boxLength - 6;
        box = addChars(blanks1, box, " ");
        box = box.concat("\\\n|  " + expenditureName);

        int blanks2 = boxLength - expenditureName.length();
        box = addChars(blanks2, box, " ");
        box = box.concat("|\n \\ " + categoryName);

        getBoxBottom(categoryName, boxLength, box, "/\n  ");
    }

    private static void getBoxBottom(String categoryName, int boxLength, String box, String lineEnding) {
        int blanks = boxLength - 1 - categoryName.length();
        box = addChars(blanks, box, " ");
        box = box.concat(lineEnding);
        box = addChars(boxLength, box, "-");
        box = box.concat("\n");
        testOutput = box;

        box = box.concat(getCow());
        setOutput(box);
    }

    /**
     * Shows the user the overall list of categories.
     * @param namesOfCategories list of category names
     * @param longestCategory number of characters in the longest category name
     */
    public static void showList(ArrayList<String> namesOfCategories, int longestCategory) {
        int boxLength = Math.max(DEFAULT_BOX, longestCategory);
        boxLength += 5;
        int blankSpace = boxLength - 33;

        String list =  ".";
        list = addChars(boxLength, list, "_");
        list = list.concat(".\n" + "|Here are your current categories.");
        list = addChars(blankSpace, list, " ");
        list = list.concat("|\n");

        for (String categoryName : namesOfCategories) {
            list = list.concat("|" + categoryName);
            blankSpace = boxLength - categoryName.length();
            list = addChars(blankSpace, list, " ");
            list = list.concat("|\n");
        }

        list = list.concat(".");
        list = addChars(boxLength, list, "-");
        list = list.concat(".\n");
        testOutput = list;

        list = list.concat(getCow());
        setOutput(list);
    }

    /**
     * Concats the input char sequence a specified number of times to a string.
     * @param length number of times to add
     * @param output string to concat char sequence
     * @param regex char sequence
     * @return output with the char sequence added
     */
    private static String addChars(int length, String output, String regex) {
        for (int i = 0; i < length; i++) {
            output = output.concat(regex);
        }
        return output;
    }

    /**
     * Prompts the user to enter what to add.
     */
    public static void showPrompt(String text) {
        print(text);
    }

    public static void printMainDisplay(String newMainDisplay) {
        print(newMainDisplay);
    }

    private static String getCow() {
        return "        \\   ^__^\n"
                + "         \\  (oo)\\_______\n"
                + "            (__)\\       )\\/\\\n"
                + "                ||----w |\n"
                + "                ||     ||\n";
    }
}
