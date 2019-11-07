package dolla.ui;

import dolla.model.Record;

public class ViewUi extends Ui {

    private String singleAnsi;

    public static void printViewSingleExpense(double amount, String desc) {
        if (amount < 0) {
            singleAnsi = ANSI_RED;
        } else if (amount > 0) {
            singleAnsi = ANSI_GREEN;
        } else {
            singleAnsi = ANSI_RESET;
        }
        System.out.println("\t[" + singleAnsi +  amount + "] [" + desc "]";
    }

    public static void printOverall(double amount) {
        
    }
}
