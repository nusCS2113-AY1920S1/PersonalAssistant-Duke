package dolla.ui;

import dolla.task.Debt;

//@@author tatayu
public class DebtUi extends Ui {

    private static final String MSG_EXISTING_DEBT = "\tOOPS! You already have the following debt:";
    private static final String MSG_MODIFY_DEBT = "\tWould you like to modify it instead?";

    /**
     * Print invalid debt format error.
     */
    public static void printInvalidDebtFormatError() {
        System.out.println(line);
        System.out.println("\tplease follow the format "
                + "'owe(/borrow) [NAME] [AMOUNT] [DESCRIPTION] /due [DURATION] {/tag [TAGNAME]}'");
        System.out.println(line);
    }

    /**
     * Prints out the current debt that already exists.
     */
    public static void existingDebtPrinter(Debt debt) {
        System.out.println(line);
        System.out.println(MSG_EXISTING_DEBT);
        System.out.println("\t" + debt.getRecordDetail());
        System.out.println(MSG_MODIFY_DEBT);
        System.out.println(line);
    }
}
