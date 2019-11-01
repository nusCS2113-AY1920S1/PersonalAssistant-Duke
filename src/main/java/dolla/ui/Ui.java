package dolla.ui;

import dolla.task.Record;

import java.util.ArrayList;

/**
 * duke.Ui is a class that handles all interactions to the user.
 */
public abstract class Ui {

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

    private static String logo =
        "\t ____    _   _  _   _       \n"
        + "\t|  _  \\/ _ \\| || | /  \\      \n"
        + "\t| | | | | | | || |/ /\\ \\  \n"
        + "\t| |_| | |_| | || |  __  | \n"
        + "\t|____/ \\ _ /|_||_|_|  |_|       \n";

    protected static String line = "\t____________________________________________________________";

    private static String version = "\tVersion 1.3\n";

    private static String welcomeMsg =
        "\tHello from\n"
        + logo
        + version
        + line
        + "\n\tI help keep track of your finance!\n"
        + "\tWhat can I do for you?";

    //private static String dollaMode = ANSI_CYAN + "\t\n°º¤ø,¸¸,ø¤º°`°º¤ø,¸ MODE: DOLLA "
    //        + "  ¸,ø¤°º¤ø,¸¸,ø¤º°`°º¤ø,¸\n" + ANSI_RESET;

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

    // TODO: DELETE
    /**
     * This method prints the strings of text from 'msg' with the proper format. Each element
     * from 'msg' is a line of text to be printed.
     *
     * @param msg ArrayList of strings containing the messages to be printed.
     */
    public static void printMsg(ArrayList<String> msg) {
        System.out.println(line);
        for (String outputMsg : msg) {
            System.out.println("\t" + outputMsg);
        }
        System.out.println(line);
    }

    // TODO: DELETE
    /**
     * This method prints the strings of text from 's' with the proper format. Each element
     * from 's' is a line of text to be printed.
     *
     * @param s containing the messages to be printed.
     */
    public static void printMsg(String... s) {
        final StringBuilder messageAccumulator = new StringBuilder();
        for (String str : s) {
            messageAccumulator.append(str);
        }
        System.out.println(line);
        System.out.println("\t" + messageAccumulator);
        System.out.println(line);
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
        System.out.println(line);
    }

    /**
     * Prints an error message when date in not in the format 'DD/MM/YYYY HHmm'.
     */
    public static void printDateTimeFormatError() {
        System.out.println(line);
        System.out.println("\tPlease use the format 'DD/MM/YYYY HHmm'!");
        System.out.println(line);
    }

    /**
     * Prints an error message when date in not in the format 'DD/MM/YYYY'.
     */
    public static void printDateFormatError() {
        System.out.println(line);
        System.out.println("\tPlease use the format 'DD/MM/YYYY'!");
        System.out.println(line);
    }

    /**
     * Prints error message when string parsed is not an integer.
     *
     * @param str to be parsed to an integer
     */
    public static void printInvalidNumberError(String str) {
        System.out.println(line);
        System.out.println("\t" + str + " is not a number. Please use a number instead!");
        System.out.println(line);
    }

    /**
     * Prints error message when command given is invalid.
     */
    public static void printInvalidCommandError() {
        System.out.println(line);
        System.out.println("\tOOPS! The command is invalid. Please enter a valid command");
        System.out.println(line);
    }

    /**
     * Print no reminder msg.
     */
    public static void printNoReminderMsg() {
        System.out.println(line);
        System.out.println("\tThere are no reminders :)");
        System.out.println(line);
    }

    //@@author
    /**
     * Print exit msg.
     */
    public static void printExitMsg() {
        System.out.println(line);
        System.out.println("\tBye. Hope to see you again soon!");
        System.out.println(line);
    }

    /**
     * Prints a message to inform user that a new tag has been added.
     * @param tag     Tag that is added.
     * @param record  Record the tag is added to.
     */
    public static void printAddedTagMsg(String tag, Record record) {
        System.out.println(line);
        System.out.println("\tNoted. I have added the tag " + tag + " to " + record.getRecordDetail());
        System.out.println(line);
    }

    /**
     * Prints a message to inform user that a new tag has been added.
     * @param tag     Tag that is added.
     */
    public static void printAddedTagMsg(String tag) {
        System.out.println(line);
        System.out.println("\tNoted. I have added the tag " + tag);
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
}