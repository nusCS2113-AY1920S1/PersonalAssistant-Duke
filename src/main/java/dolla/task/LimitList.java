package dolla.task;

import dolla.DollaData;
import dolla.ModeStringList;
import dolla.Storage;

import java.util.ArrayList;

/**
 * A class that contains methods regarding the LimitList (add, remove, check for existing limits).
 */
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

    public int findExistingLimitIndex(DollaData dollaData, String limitType, String limitDuration) {
        int index = - 1;
        LimitList limitList = (LimitList) dollaData.getRecordList(ModeStringList.MODE_LIMIT);
        Limit currLimit;
        String currType, currDuration;
        for (int i = 0; i < limitList.size(); i++) {
            currLimit = (Limit) limitList.getFromList(i);
            currType = currLimit.type;
            currDuration = currLimit.duration;
            if (currType.equals(limitType) && currDuration.equals(limitDuration)) {
                index = i;
                break;
            }
        }
        return index;
    }
}