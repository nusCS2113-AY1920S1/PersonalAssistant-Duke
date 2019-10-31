package dolla.ui;

//@@author tatayu
public class DebtUi extends Ui {

    /**
     * Print invalid debt format error.
     */
    public static void printInvalidDebtFormatError() {
        System.out.println(line);
        System.out.println("\tplease follow the format "
                + "'owe(/borrow) [NAME] [AMOUNT] [DESCRIPTION] /due [DURATION] {/tag [TAGNAME]}'");
        System.out.println(line);
    }
}
