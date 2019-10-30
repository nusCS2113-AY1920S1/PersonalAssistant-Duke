package dolla.ui;

import javax.rmi.ssl.SslRMIClientSocketFactory;

//@@author: tatayu
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

    public static void printAverageAmount(int people, double amount) {
        System.out.println((line));
        System.out.println("Here is the bill per person after splitting: " + "$" + amount/people);
        System.out.println(line);
    }
}
