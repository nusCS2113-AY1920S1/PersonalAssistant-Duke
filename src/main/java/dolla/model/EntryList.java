package dolla.model;

import dolla.storage.StorageWrite;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Holds all the entries that have been added to Dolla.
 */
public class EntryList extends RecordList {
    public EntryList(ArrayList<Record> importEntryList) {
        super(importEntryList);
    }

    @Override
    public void add(Record newRecord) {
        super.add(newRecord);
        StorageWrite.setEntries(get()); //save
    }

    @Override
    public void removeFromList(int index) {
        super.removeFromList(index);
        StorageWrite.setEntries(get());
    }

    @Override
    public void setRecordList(ArrayList<Record> recordList) {
        this.list = recordList;
        StorageWrite.setEntries(get());
    }

    /**
     * Method to check if input entry already exists.
     * @param dollaData The storage container for all the lists.
     * @param inputRecord The entry being input by user.
     * @param mode The mode the user is on (entry).
     * @return index of the currently existing entry (is - 1 if not found)
     */
    @Override
    public int findExistingRecordIndex(DollaData dollaData, Record inputRecord, String mode) {
        Entry entry = (Entry) inputRecord;
        int index = - 1;
        EntryList entryList = (EntryList) dollaData.getRecordListObj(mode);
        for (int i = 0; i < entryList.size(); i++) {
            Entry currEntry = (Entry) (entryList.getFromList(i));
            String currType = currEntry.type;
            double currAmount = currEntry.amount;
            String currDescription = currEntry.description;
            LocalDate currDate = currEntry.date;
            if (currType.equals(entry.type)
                && currAmount == entry.amount
                && currDescription.equals(entry.description)
                && currDate.equals(entry.date)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public void addWithIndex(int modifyIndex, Record newRecord) {
        super.addWithIndex(modifyIndex, newRecord);
        StorageWrite.setEntries(get());
    }

    /*
    public double dateOverallExpense(LocalDate cmpDate) {
        double sum = 0;
        for (int i = 0; i < list.size(); i += 1) {
            Record currEntry = list.get(i);
            LocalDate currDate = currEntry.getDate();
            if (isSameDate(currDate, cmpDate)) {
                double change = currEntry.getAmount();
                change = (currEntry.getType().equals("expense") ? change*-1 : change);
                sum += change;
            }
        }
        return sum;
    }
    */

    private boolean isSameDate(LocalDate d1, LocalDate d2) {
        if (d1.compareTo(d2) == 0) {
            return true;
        } else {
            return false;
        }
    }
}
