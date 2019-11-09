package dolla.storage;

import dolla.model.Record;
import dolla.ui.Ui;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//@@author yetong1895
/**
 * duke.Storage handles the saving and loading of data from ./data/duke.txt,
 * as well as creating a new save file if it does not exist.
 */
public class Storage implements StorageStringList {

    protected static ArrayList<Record> entries = new ArrayList<Record>();
    protected static ArrayList<Record> limits = new ArrayList<Record>();
    protected static ArrayList<Record> debts = new ArrayList<Record>();
    protected static ArrayList<Record> shortcuts = new ArrayList<Record>();
    protected static ArrayList<Record> bills = new ArrayList<Record>();
    protected static ArrayList<Record> storage = new ArrayList<Record>();

    protected static double stringToDouble(String str) {
        double newDouble = 0.0;
        try {
            newDouble = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            Ui.printInvalidNumberError(str);
        }
        return newDouble;
    }

    //@@author yetong1895
    /**
     * This method will return the ArrayList containing the entries.
     * @return entries the ArrayList containing all the entries.
     */
    public static ArrayList<Record> getEntriesFromSave() {
        return entries;
    }

    /**
     * This method will return the ArrayList containing the limits.
     * @return limits the ArrayList containing all the limits.
     */
    public static ArrayList<Record> getLimitsFromSave() {
        return limits;
    }

    /**
     * This method will return the ArrayList containing the debts.
     * @return entries the ArrayList containing all the debts.
     */
    public static ArrayList<Record> getDebtsFromSave() {
        return debts;
    }

    /**
     * This method will return the ArrayList containing the debts.
     * @return bills the ArrayList containing all the bills.
     */
    public static ArrayList<Record> getBillsFromSave() {
        return bills;
    }

    /**
     * This method will return the ArrayList containing the shortcuts.
     * @return entries the ArrayList containing all the shortcuts.
     */
    public static ArrayList<Record> getShortcutsFromSave() {
        return shortcuts;
    }

    /**
     * This method will clear the data in the save file.
     */
    public static void clearStorage() {
        try {
            FileWriter file = new FileWriter(PATH,false);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
