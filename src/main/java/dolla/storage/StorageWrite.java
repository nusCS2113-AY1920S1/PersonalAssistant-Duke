package dolla.storage;

import dolla.model.Record;
import dolla.ui.StorageUi;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class StorageWrite extends Storage {

    /**
     * This method will set the ArrayList of entries in this class.
     * @param entries the ArrayList this method going to set to.
     */
    public static void setEntries(ArrayList<Record> entries) {
        Storage.entries = entries;
        save();
    }

    /**
     * This method will set the ArrayList of limits in this class.
     * @param limits the ArrayList this method going to set to.
     */
    public static void setLimits(ArrayList<Record> limits) {
        Storage.limits = limits;
        save();
    }

    /**
     * This method will set the ArrayList of debts in this class.
     * @param debts the ArrayList this method is going to set to.
     */
    public static void setDebts(ArrayList<Record> debts) {
        Storage.debts = debts;
        save();
    }

    /**
     * This method will set the ArrayList of bills in this class.
     * @param bills the ArrayList this method is going to set to.
     */
    public static void setBill(ArrayList<Record> bills) {
        Storage.bills = bills;
        save();
    }

    /**
     * This method will set the ArrayList of debts in this class.
     * @param shortcuts the ArrayList this method going to set to.
     */
    public static void setShortcuts(ArrayList<Record> shortcuts) {
        Storage.shortcuts = shortcuts;
        save();
    }

    /**
     * This method will save all the ArrayList into an external text file.
     */
    protected static void save() {
        try (FileWriter file = new FileWriter(PATH)) {
            storage.addAll(entries);
            storage.addAll(debts);
            storage.addAll(limits);
            storage.addAll(shortcuts);
            storage.addAll(bills);

            for (Record currSave : storage) {
                String fileContent = currSave.formatSave();
                file.write(fileContent);
                file.write(System.lineSeparator());
            }
            storage.clear();

        } catch (IOException e) {
            StorageUi.printErrorWritingSaveMessage();
        }
    }
}
