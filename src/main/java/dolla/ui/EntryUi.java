package dolla.ui;

import dolla.task.Entry;

//@@author omupenguin
public class EntryUi extends Ui {

    private static final String MSG_EXISTING_ENTRY = "\tOOPS! You already have the following entry:";
    private static final String MSG_MODIFY_ENTRY = "\tWould you like to modify it instead?";

    /**
     * Prints error message when the user did not properly specify the type of entry to add.
     */
    public static void printInvalidEntryType() {
        System.out.print(line);
        System.out.println("\tPlease specify the type of entry you want to add: income or expense.");
        System.out.print(line);
    }

    /**
     * Prints error message when user tries to add income/expense, but inputs the wrong format.
     */
    public static void printInvalidEntryFormatError() {
        System.out.println(line);
        System.out.println("\tplease follow the format "
                + "'add income(/expense) [AMOUNT] [DESCRIPTION] /on [DATE] {/tag [TAG]}'"
                + "");
        System.out.println(line);
    }

    /**
     * Prints out the current entry that already exists.
     */
    public static void existingEntryPrinter(Entry entry) {
        System.out.println(line);
        System.out.println(MSG_EXISTING_ENTRY);
        System.out.println("\t" + entry.getRecordDetail());
        System.out.println(MSG_MODIFY_ENTRY);
        System.out.println(line);
    }
}
