package dolla.task;

import java.lang.reflect.Array;
import java.util.ArrayList;

//@@author tatayu
/**
 * Bill is a Class that stores an instance of the user's bill split case.
 */
public class Bill extends Record {

    protected String type;
    protected String name;
    protected int people;
    protected double amount;
    protected double average;
    protected ArrayList<String> nameList;

    /**
     * Instantiates a new bill.
     * @param type Type of the debt (is bill in this case).
     * @param people The number of people.
     * @param amount The total amount.
     * @param average The average amount per person after splitting.
     * @param nameList The name list.
     */
    public Bill(String type, int people, double amount, double average, ArrayList<String> nameList) {
        this.type = type;
        this.people = people;
        this.amount = amount;
        this.average = amount / people;
        this.nameList = nameList;
    }

    /**
     * Returns a string to with information about the bill to be displayed
     * to the user.
     * @return String with information of bill
     */
    @Override
    public String getRecordDetail() {
        return "[" + type + "] "
                + "[" + people + " pax] "
                + "[" + "Total: $" + amount + "] "
                + "[" + amountToMoney() + " per person] "
                + nameList;
    }

    /**
     * Returns a string with information about the bill to be saved.
     * @return String with information of bill in save format.
     */
    @Override
    public String formatSave() {
        return "BI" + " | "
                + people + " | "
                + amount + " | "
                + amount / people + " | "
                + nameString(nameList);
    }

    @Override
    public String amountToMoney() {
        return "$" + amount / people;
    }

    /**
     * This method changes the arraylist of name to a string.
     * @param nameList the name list.
     * @return a string that contains all the name from the nameList.
     */
    public String nameString(ArrayList<String> nameList) {
        String nameStore = new String();
        for (int i = 0; i < nameList.size(); i++) {
            if (i == nameList.size() - 1) {
                nameStore += nameList.get(i);
            } else {
                nameStore += nameList.get(i) + ", ";
            }
        }
        return nameStore;
    }
}
