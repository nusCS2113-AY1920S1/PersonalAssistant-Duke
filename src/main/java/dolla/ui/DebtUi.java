package dolla.ui;

import dolla.model.RecordList;

import java.util.ArrayList;

//@@author tatayu
public class DebtUi extends Ui {

    /**
     * Print invalid debt format error.
     */
    public static void printInvalidDebtFormatError() {
        System.out.println(line);
        System.out.println("\tplease follow the format "
                + "'owe(/borrow) [NAME] [AMOUNT] [DESCRIPTION] /due [DURATION]'");
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
     * Print error message for invalid bill input.
     */
    public static void printInvalidBillFormatError() {
        System.out.println(line);
        System.out.println("\tplease follow the format "
                + "'bill [NUMBER OF PEOPLE] [TOTAL AMOUNT] [NAME 1] [NAME 2]...[NAME NUMBER OF PEOPLE]");
        System.out.println(line);
    }

    /**
     * Print message after deleting a name.
     * @param name name of the person who paid the bill.
     * @param nameList the name list.
     */
    public static void printRemoveNameMessage(String name, ArrayList<String> nameList) {
        System.out.println(line);
        System.out.println("\tGot it! " + name + " has paid the bill.");
        System.out.println("\tHere is the updated name list: " + nameList);
        System.out.println(line);
    }

    /**
     * Print invalid paid format.
     */
    public static void printInvalidPaidFormatError() {
        System.out.println(line);
        System.out.println("\tPlease follow the format: paid [LIST NUMBER] [NAME]");
        System.out.println(line);
    }

    /**
     * Print invalid bill number message.
     */
    public static void printInvalidBillNumberError() {
        System.out.println(line);
        System.out.println("\tPlease input a valid bill number.");
        System.out.println(line);
    }

    /**
     * Print message to notify a bill that has been paid by everyone.
     */
    public static void printFinishMessage() {
        System.out.println(line);
        System.out.println("\tOne bill has cleared");
        System.out.println(line);
    }

    /**
     * Print empty bill message.
     */
    public static void printEmptyBillMessage() {
        System.out.println(line);
        System.out.println("\tYou don't have any bills yet.");
        System.out.println(line);
    }

    /**
     * print wrong number of people for bill message.
     */
    public static void printWrongPeopleNumberMessage(int people) {
        System.out.println(line);
        System.out.println("\tPlease enter " + people + " names.");
    }

    /**
     * Print invalid name message.
     */
    public static void printInvalidNameMessage() {
        System.out.println(line);
        System.out.println("\tPlease input a valid name.");
        System.out.println(line);
    }

    /**
     * print name not found message.
     */
    public static void printNameNotFound() {
        System.out.println(line);
        System.out.println("\tThis name is not found in the list.");
        System.out.println(line);
    }

    /**
     * print to tell the user that bill feature is used under debt mode.
     */
    public static void printBillFeature() {
        System.out.println(line);
        System.out.println("\tYou can also add bills under this mode :)");
        System.out.println(line);
    }

    //@@ author: omupenguin
    /**
     * Prints an error message informing the user that the type of debt entered is invalid.
     */
    public static void printInvalidDebtType() {
        System.out.println(line);
        System.out.println("\tThe type can only be 'owe' or 'borrow'.");
        System.out.println(line);
    }
}
