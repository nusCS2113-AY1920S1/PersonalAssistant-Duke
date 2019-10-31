package dolla.task;

import java.lang.reflect.Array;
import java.util.ArrayList;

//@@author tatayu
public class Bill extends Record {

    protected String type;
    protected String name;
    protected int people;
    protected double amount;
    protected double average;
    protected ArrayList<String> nameList;

    public Bill(String type, int people, double amount, double average, ArrayList<String> nameList) {
        this.type = type;
        this.people = people;
        this.amount = amount;
        this.average = amount/people;
        this.nameList = nameList;
    }

    @Override
    public String getRecordDetail() {
        return "[" + type + "] "
                + "[" + people + " pax] "
                + "[" + "Total: $" + amount + "] "
                + "[" + amountToMoney() + " per person] "
                + nameList;
    }

    @Override
    public String formatSave() {
        return "BI" + " | " +
                people + " | " +
                amount + " | " +
                amount/people + " | " +
                nameString(nameList);
    }

    @Override
    public String amountToMoney() {
        return "$" + amount/people;
    }

    public String nameString(ArrayList<String> nameList) {
        String nameStore = new String();
        for(int i = 0; i < nameList.size(); i++) {
            if(i == nameList.size() - 1){
                nameStore += nameList.get(i);
            } else {
                nameStore += nameList.get(i) + ", ";
            }
        }
        return nameStore;
    }
}
