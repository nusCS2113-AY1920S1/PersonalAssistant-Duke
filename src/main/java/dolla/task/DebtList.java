package dolla.task;

import dolla.DollaData;
import dolla.storage.StorageWrite;

import java.time.LocalDate;
import java.util.ArrayList;

//@@author tatayu
/**
 * Holds all the debts that have been added to Dolla.
 */
public class DebtList extends RecordList {

    public DebtList(ArrayList<Record> importDebtList) {
        super(importDebtList);
    }

    @Override
    public void add(Record newRecord) {
        super.add(newRecord);
        StorageWrite.setDebts(get()); //save
    }

    @Override
    public void removeFromList(int index) {
        super.removeFromList(index);
        StorageWrite.setDebts(get()); //save
    }

    @Override
    public void setRecordList(ArrayList<Record> recordList) {
        this.list = recordList;
        StorageWrite.setDebts(get());
    }

    /**
     * Method to check if input debt already exists.
     * @param dollaData The storage container for all the lists.
     * @param inputRecord The debt being input by the user.
     * @param mode The mode the user is on (debt).
     * @return index of the currently existing debt (is - 1 if not found)
     */
    @Override
    public int findExistingRecordIndex(DollaData dollaData, Record inputRecord, String mode) {
        Debt debt = (Debt) inputRecord;
        int index = - 1;
        DebtList debtList = (DebtList) dollaData.getRecordList(mode);
        for (int i = 0; i < debtList.size(); i++) {
            Debt currDebt = (Debt) (debtList.getFromList(i));
            String currType = currDebt.type;
            String currName = currDebt.name;
            double currAmount = currDebt.amount;
            String currDescription = currDebt.description;
            LocalDate currDate = currDebt.date;
            if (currType.equals(debt.type)
                && currAmount == debt.amount
                && currDescription.equals(debt.description)
                && currDate.equals(debt.date)
                && currName.equals(debt.name)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public void addWithIndex(int modifyIndex, Record newRecord) {
        super.addWithIndex(modifyIndex, newRecord);
        StorageWrite.setDebts(get());
    }
}
