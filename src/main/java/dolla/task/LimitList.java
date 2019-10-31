package dolla.task;

import dolla.DollaData;
import dolla.ModeStringList;
import dolla.Storage;

import java.util.ArrayList;

/**
 * A class that contains methods regarding the LimitList (add, remove, check for existing limits).
 */
//@@author Weng-Kexin
public class LimitList extends RecordList {

    public LimitList(ArrayList<Record> importLimitList) {
        super(importLimitList);
    }

    @Override
    public void add(Record newRecord) {
        super.add(newRecord);
        Storage.setLimits(get()); //save
    }

    @Override
    public void removeFromList(int index) {
        super.removeFromList(index);
        Storage.setLimits(get()); //save
    }

    /**
     * Method checks to see if the input limit already exists.
     * @param dollaData The storage container for all the lists.
     * @param inputLimit The limit being input by user.
     * @param mode The mode the user is on.
     * @return index of the currently existing limit (is - 1 if not found)
     */
    public int findExistingLimit(DollaData dollaData, Limit inputLimit, String mode) {
        int index = - 1;
        LimitList limitList = (LimitList) dollaData.getRecordList(mode);
        Limit currLimit;
        String currType;
        String currDuration;
        for (int i = 0; i < limitList.size(); i++) {
            currLimit = (Limit) limitList.getFromList(i);
            currType = currLimit.type;
            currDuration = currLimit.duration;
            if (currType.equals(inputLimit.type) && currDuration.equals(inputLimit.duration)) {
                index = i;
                break;
            }
        }
        return index;
    }
}