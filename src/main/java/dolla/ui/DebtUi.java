package dolla.ui;

import dolla.task.RecordList;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.util.ArrayList;

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

    public static void printAverageAmount(int people, double amount, ArrayList<String> nameList) {
        System.out.println((line));
        System.out.println("\tGot it! Total amount: $" + amount + " Number of people: " + people);
        System.out.println("\tHere is the bill per person after splitting: " + "$" + amount/people);
        System.out.println("\tHere is the name list: ");
        for(int i = 0 ; i < people; i ++) {
            System.out.println("\t" + i+1 + ". " + nameList.get(i));
        }
        System.out.println(line);
    }

    public static void printBillList(RecordList recordList) {

        System.out.println(line);
        System.out.println("\tHere is the list of bills you have added:");
        for (int i = 0; i < recordList.size(); i++) {
            int listNum = i + 1;
            System.out.println("\t" + listNum + ". " + recordList.get().get(i).getRecordDetail());
        }
        System.out.println(line);
    }
}
