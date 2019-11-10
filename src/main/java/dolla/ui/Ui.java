package dolla.ui;

import dolla.model.Record;

import static dolla.ModeStringList.MODE_SHORTCUT;

/**
 * duke.Ui is a class that handles all interactions to the user.
 */
public abstract class Ui {

    protected static final String MSG_MODIFY = "\tPlease use the format 'modify [LIST NUM]' if you wish to modify it.";
    private static final String EXISTING_RECORD_MSG = "\tOOPS! You already have the following ";
    private static final String INVALID_AMOUNT_MSG = "\tOOPS! The amount you have entered is invalid.";
    private static final String VALID_AMOUNT_MSG = "\tPlease key in a non-zero positive value with an appropriate "
                                                  + "decimal point that is less than 1,000,000.";
    private static final String EXECUTE_SHORTCUT_MSG = "\tYou can execute 'shortcuts' to view your list of shortcuts!";
    private static final String INVALID_DATE_MSG = "\tPlease use the format 'DD/MM/YYYY'!";
    private static final String INVALID_NUMBER_MSG = " is not a number. Please use a number instead!";
    private static final String INVALID_COMMAND_MSG = "\tOOPS! The command is invalid. Please enter a valid command!";

    private static final String NO_REMINDERS_MSG = "\tThere are no reminders :)";
    private static final String EXIT_MSG = "\tBye. Hope to see you again soon!";


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    protected static final String SORT_DESCRIPTION = "description";
    protected static final String SORT_DATE = "date";
    protected static final String SORT_NAME = "name";

    private static String newLogo =
            "\t /$$$$$$$            /$$ /$$  \n"
          + "\t| $$__  $$          | $$| $$   \n"
          + "\t| $$  \\ $$  /$$$$$$ | $$| $$  /$$$$$$ \n"
          + "\t| $$  | $$ /$$__  $$| $$| $$ |____  $$\n"
          + "\t| $$  | $$| $$  \\ $$| $$| $$  /$$$$$$$\n"
          + "\t| $$  | $$| $$  | $$| $$| $$ /$$__  $$\n"
          + "\t| $$$$$$$/|  $$$$$$/| $$| $$|  $$$$$$$\n"
          + "\t|_______/  \\______/ |__/|__/ \\_______/\n";

    protected static final String line = "\t____________________________________________________________";

    private static final String version = "\tVersion 1.4\n";

    private static String welcomeMsg =
        "\tHello from\n"
        + newLogo
        + version
        + line
        + "\n\tI help keep track of your finance!\n"
        + "\tWhat can I do for you?";

    private static String dollaMode = ANSI_CYAN + "\n\t( o_o)O ~~ MODE: DOLLA "
            + " ~~ \n" + ANSI_RESET;

    /**
     * Prints DOLLA logo and welcome message.
     */
    public static void showWelcome() {
        System.out.println(welcomeMsg);
        System.out.println(line);
        System.out.println(dollaMode);
    }

    /**
     * Prints Dolla's new mode after being updated.
     *
     * @param newMode The new mode to be switched.
     */
    public static void printModeUpdated(String newMode) {
        System.out.println(line);
        System.out.println("\tGot it! Mode changed to " + newMode + "!");
        System.out.println(line);
        System.out.println();
        //System.out.println(ANSI_CYAN + "\t°º¤ø,¸¸,ø¤º°`°º¤ø,¸ MODE:  " + newMode.toUpperCase()
        //        + "  ¸,ø¤°º¤ø,¸¸,ø¤º°`°º¤ø,¸\n" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "\t( *_*)O ~~ MODE:  " + newMode.toUpperCase()
                + "  \n" + ANSI_RESET);
    }

    /**
     * Prints the details of the specified record and is typically called when a new record is entered,
     * so that the user can check the details of the created record.
     *
     * @param currRecord record to be printed, can be an entry, limit or debt.
     */
    public static void echoAddRecord(Record currRecord) {
        System.out.println(line);
        System.out.println("\tGot it. I've added this " + currRecord.getRecordType() + ": ");
        System.out.println("\t" + currRecord.getRecordDetail());
        if (currRecord.getRecordType().equals(MODE_SHORTCUT)) {
            System.out.println(EXECUTE_SHORTCUT_MSG);
        }
        System.out.println(line);
    }

    /**
     * Prints an error message when date in not in the format 'DD/MM/YYYY'.
     */
    public static void printDateFormatError() {
        System.out.println(line);
        System.out.println(INVALID_DATE_MSG);
        System.out.println(line);
    }

    /**
     * Prints error message when string parsed is not an integer.
     *
     * @param str to be parsed to an integer
     */
    public static void printInvalidNumberError(String str) {
        System.out.println(line);
        System.out.println("\t" + str + INVALID_NUMBER_MSG);
        System.out.println(line);
    }

    /**
     * Prints error message when command given is invalid.
     */
    public static void printInvalidCommandError() {
        System.out.println(line);
        System.out.println(INVALID_COMMAND_MSG);
        System.out.println(line);
    }

    /**
     * Print no reminder msg.
     */
    public static void printNoReminderMsg() {
        System.out.println(line);
        System.out.println(NO_REMINDERS_MSG);
        System.out.println(line);
    }

    //@@author omupenguin
    /**
     * Print exit msg.
     */
    public static void printExitMsg() {
        System.out.println(line);
        System.out.println(EXIT_MSG);
        System.out.println(line);
    }

    /**
     * Prints a message to inform user that the command is not implemented yet.
     */
    public static void printUpcomingFeature() {
        System.out.println(line);
        System.out.println("\tSorry, I can't do that yet. Stay tuned for a later version and maybe my creators"
                + "will teach me how to do that. :(");
        System.out.println(line);
    }

    //@@author yetong1895
    /**
     * This method will let the thread sleep for the "time" amount of time.
     * @param time the time for the thread to sleep
     */
    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Prints input String in typewriter style.
     * @param message the message to print in typewriter style.
     */
    public static void typewriter(String message) {
        for (int i = 0; i < message.length(); i++) {
            System.out.printf("%c",message.charAt(i));
            sleep(10);
        }
    }

    /**
     * Prints the error message if user is trying trying to remove a non-existing record.
     * @param number the total number of record.
     */
    public static void printNumberOfRecords(int number) {
        System.out.println(line);
        System.out.println("\tSorry, you only have " + number + " record(s).");
        System.out.println(line);
    }

    public static void printDateRequest() {
        System.out.println("\tPlease enter your new entry date in the format 'DD/MM/YYYY'");
    }

    /**
     * Prints a message with the related details about an existing record.
     * @param record The existing record.
     * @param mode   The mode Dolla is on.
     */
    public static void existingRecordPrinter(Record record, String mode) {
        System.out.println(line);
        System.out.println(EXISTING_RECORD_MSG + mode + ":");
        System.out.println("\t" + record.getRecordDetail());
        System.out.println(MSG_MODIFY);
        System.out.println(line);
    }

    /**
     * Prints a message reminding user to input a valid amount.
     */
    public static void invalidAmountPrinter() {
        System.out.println(line);
        System.out.println(INVALID_AMOUNT_MSG);
        System.out.println(VALID_AMOUNT_MSG);
        System.out.println(line);
    }


}