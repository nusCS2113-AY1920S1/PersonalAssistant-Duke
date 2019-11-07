package dolla.model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A class that is inherited from all forms of recording, ie. entry, Limit and debt so that we can perform polymorphism.
 */
public abstract class Record {

    protected String type;
    protected String description;
    protected LocalDate date;
    protected String name;
    protected String userInput;
    protected double amount;
    protected String recordType;
    protected ArrayList<String> nameList;
    protected int people;
    protected String tagName;

    protected static final String RECORD_ENTRY = "entry";
    protected static final String RECORD_LIMIT = "limit";
    protected static final String RECORD_DEBT = "debt";
    protected static final String RECORD_SHORTCUT = "shortcut";

    protected String duration;

    public abstract String getRecordDetail();

    public abstract String formatSave();//currently assuming no tags

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getRecordType() {
        return recordType;
    }

    public String getUserInput() {
        return userInput;
    }

    public String getDuration() {
        return duration;
    }

    public double getAmount() {
        return amount;
    }

    /*
    public String getTagName() {
        return tagName;
    }
     */

    public void setDescription(String s) {
        description = s;
    }

    public void setAmount(double d) {
        amount = d;
    }

    public void setType(String s) {
        type = s;
    }

    public void setDate(LocalDate ld) {
        date = ld;
    }

    public void setTagName(String inputTag) {
        tagName = inputTag;
    }

    public ArrayList<String> getNameList() {
        return nameList;
    }

    public int getPeople() {
        return people;
    }

    public double getBillAmount() {
        return amount;
    }

    public abstract String amountToMoney();
}
