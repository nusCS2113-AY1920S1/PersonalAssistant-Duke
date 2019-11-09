package moomoo.feature;

import moomoo.feature.category.Category;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.io.PrintStream;
import java.util.Scanner;


/**
 * Represents the User Interface to be shown to the user.
 */
public class Ui {
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static String output = null;
    private static Scanner inputScanner;

    /**
     * Prints out a message.
     * @param text message to be printed
     */
    private static void print(String text) {
        System.out.println(text);
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
     * Returns the value to be printed to the GUI.
     * @return String to be printed on the GUI
     */
    public static String returnResponse() {
        return output;
    }

    /**
     * Prints the welcome message to the User.
     */
    public static void showWelcome() {
        print("   \n"
                + "   ^____^________\n"
                + "   ( oo )\\ *  *  )\\/\\\n"
                + "   (____)||----w |  o \n"
                + "         ||     ||   00\n"
                + "   wmwwmWMWMwmWMmwMWWMWMwm\n"
                + " __      _____ _    ___ ___  __  __ ___   _____ ___ \n"
                + " \\ \\    / / __| |  / __/ _ \\|  \\/  | __| |_   _/ _ \\\n"
                + "  \\ \\/\\/ /| _|| |_| (_| (_) | |\\/| | _|    | || (_) |\n"
                + "   \\_/\\_/ |___|____\\___\\___/|_|  |_|___|   |_| \\___/ \n"
                + "\n"
                + " __  __  ___   ___  __  __  ___   ___  __  __  ___  _  _ _____   __\n"
                + "|  \\/  |/ _ \\ / _ \\|  \\/  |/ _ \\ / _ \\|  \\/  |/ _ \\| \\| | __\\ \\ / /\n"
                + "| |\\/| | (_) | (_) | |\\/| | (_) | (_) | |\\/| | (_) | .` | _| \\ V /\n"
                + "|_|  |_|\\___/ \\___/|_|  |_|\\___/ \\___/|_|  |_|\\___/|_|\\_|___| |_|\n"
                + "\n"
                + "Your one-stop budgeting and expenses tracker!\n"
                + "What can MooMoo do for you today?");
    }
    

    /**
     * Used to read input from the user.
     * @return String representing the input given by the User
     */
    public static String readCommand() {
        inputScanner = new Scanner(System.in);
        return inputScanner.nextLine().trim();
    }

    /**
     * Used to read input from the user.
     * @return Integer representing the input given by the User
     */
    public static int readNumber() {
        inputScanner = new Scanner(System.in);
        return inputScanner.nextInt();
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
     * Sets the myOutput to be printed.
     * @param myOutput Input value to be printed.
     */
    public static void setOutput(String myOutput) {
        output = myOutput;
    }

    /**
     * Prints out when a new category is created.
     * @param categoryName name of the new category
     */
    public static void showCategoryMessage(String categoryName) {
        String blankSpace = " ";
        int blanks = 50 - categoryName.length();
        for (int i = 0; i < blanks; i++) {
            blankSpace += " ";
        }
        output =
                " ____________________________________________________\n"
                + "/ Mooo.                                              \\\n"
                + "\\ " + categoryName + blankSpace + "/\n"
                + " ----------------------------------------------------\n"
                + "        \\   ^__^\n"
                + "         \\  (oo)\\_______\n"
                + "            (__)\\       )\\/\\\n"
                + "                ||----w |\n"
                + "                ||     ||\n";
    }

    /**
     * Prints out when a new expenditure is created.
     * @param categoryName name of the new expenditure
     */
    public static void showNewExpenditure(String expenditureName, String categoryName) {
        String blankSpace = " ";
        int blanks = 18 - expenditureName.length();
        for (int i = 0; i < blanks; i++) {
            blankSpace += " ";
        }
        String blank2 = " ";
        blanks = 32 - categoryName.length();
        for (int i = 0; i < blanks; i++) {
            blank2 += " ";
        }
        String output =
                "  _________________________________________________\n"
                + " / Mooo.                                           \\\n"
                + "|  New expenditure named : " + expenditureName + " added" + blankSpace + "|\n"
                + " \\ To category : " + categoryName + "." + blank2 + "/\n"
                + "  -------------------------------------------------\n"
                + "        \\   ^__^\n"
                + "         \\  (oo)\\_______\n"
                + "            (__)\\       )\\/\\\n"
                + "                ||----w |\n"
                + "                ||     ||\n";
        print(output);
    }

    /**
     * Promts the user to enter a category index.
     */
    public static void showEnterCategoryMessage() {
        print("Please enter the index of a category.");
    }

    /**
     * Promts the user to enter the number corresponding to a month.
     */
    public static void showEnterMonthMessage() {
        print("Please enter a month in the format MM.");
    }

    /**
     * Shows the user his total spending for the month in a category.
     * @param monthlyTotal total spending
     * @param category category user wants to check
     * @param month month that should be totaled
     */
    public static void showMonthlyTotal(double monthlyTotal, Category category, int month) {
        String cow =
                ".__________________________________.\n"
                + "|Month : " + month + "blank" + "|\n"
                + "|Category : " + category.name() + "|\n"
                + "|                                  |\n"
                + "|Total spending : $" + monthlyTotal + "|\n"
                + ".----------------------------------.\n"
                + "        \\   ^__^\n"
                + "         \\  (oo)\\_______\n"
                + "            (__)\\       )\\/\\\n"
                + "                ||----w |\n"
                + "                ||     ||\n";

        print(cow);
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
}
