package dolla.ui;

import dolla.task.RecordList;
import dolla.task.Debt;

import java.util.ArrayList;

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
     * Print the echo message after adding a bill.
     * @param people The number of people.
     * @param amount The total amount.
     * @param nameList THe name list.
     */
    public static void printAverageAmount(int people, double amount, ArrayList<String> nameList) {
        System.out.println((line));
        System.out.println("\tGot it! Total amount: $" + amount + " Number of people: " + people);
        System.out.println("\tHere is the bill per person after splitting: " + "$" + amount / people);
        System.out.println("\tHere is the name list: ");
        for (int i = 0; i < people; i++) {
            System.out.println("\t" + (i + 1) + ". " + nameList.get(i));
        }
        System.out.println(line);
    }

    /**
     * Print the bill list.
     * @param recordList the list that stores all the bill added by the user.
     */
    public static void printBillList(RecordList recordList) {
        System.out.println(line);
        System.out.println("\tHere is the list of bills you have added:");
        for (int i = 0; i < recordList.size(); i++) {
            int listNum = i + 1;
            System.out.println("\t" + listNum + ". " + recordList.get().get(i).getRecordDetail());
        }
    }

    /**
     *Prints out the current debt that already exists.
     */
    public static void existingDebtPrinter(Debt debt) {
        System.out.println(line);
        System.out.println(MSG_EXISTING_DEBT);
        System.out.println("\t" + debt.getRecordDetail());
        System.out.println(MSG_MODIFY_DEBT);
        System.out.println(line);
    }

    /**
     * Print error message for invalid bill input.
     */
    public static void printInvalidBillFormatError() {
        System.out.println(line);
        System.out.println("\tplease follow the format "
                + "'bill [NUMBER OF PEOPLE] [TOTAL AMOUNT] [NAME1] [NAME2]...'");
        System.out.println(line);
    }
}
