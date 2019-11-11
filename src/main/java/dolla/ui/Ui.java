package dolla.ui;

import dolla.model.Record;

import static dolla.ModeStringList.MODE_DEBT;
import static dolla.ModeStringList.MODE_ENTRY;
import static dolla.ModeStringList.MODE_LIMIT;
import static dolla.ModeStringList.MODE_SHORTCUT;

/**
 * Ui is an class that handles all interactions to the user.
 */
public abstract class Ui implements UiStrings {

    //todo: remove
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    /**
     * Prints DOLLA logo and welcome message.
     */
    public static void showWelcome() {
        System.out.println(welcomeMsg);
        System.out.println(line);
        System.out.println(dollaModeLogo);
    }

    private static String getModeLogo(String mode) {
        switch (mode) {
        case MODE_ENTRY:
            return entryModeLogo;
        case MODE_LIMIT:
            return limitModeLogo;
        case MODE_DEBT:
            return debtModeLogo;
        case MODE_SHORTCUT:
            return shortcutModeLogo;
        default:
            return dollaModeLogo;
        }
    }

    /**
     * Prints Dolla's new mode after being updated.
     *
     * @param newMode The updated mode.
     */
    public static void printModeUpdated(String newMode) {
        System.out.println(line);
        System.out.println(CHANGE_MODE_MSG);
        String modeStr = getModeLogo(newMode);
        System.out.println(modeStr);
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
        System.out.println(TAB + str + INVALID_NUMBER_MSG);
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
        System.out.println(DATE_REQ_MSG);
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