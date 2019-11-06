package dolla.ui;

//@@author omupenguin
public class EntryUi extends Ui {

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
                + "'add income(/expense) [AMOUNT] [DESCRIPTION] /on [DATE] {/tag [TAG]}'");
        System.out.println(line);
    }
}
