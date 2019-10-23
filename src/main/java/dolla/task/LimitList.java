package dolla.task;

import dolla.Storage;
import dolla.task.Log;

import java.util.ArrayList;

/**
 * A class that contains methods regarding the Limit List.
 */
public class LimitList extends LogList {

    public LimitList(ArrayList<Log> importLimitList) {
        super(importLimitList);
    }

    /*
    public boolean limitFinder(ArrayList<Limit> list, Limit limit) {
        boolean limitIsFound = false;
        for (int i = 0; i < list.size(); i++) {
            Limit currLimit = list.get(i);
            if (limit.duration == currLimit.duration && limit.type == currLimit.type) {
                limitIsFound = true;
            }
        }
        return limitIsFound;
    }
    */

    //ADD TO LIST
    public void addToBudget() {

    }

    @Override
    public void add(Log newLog) {
        super.add(newLog);
        Storage.setLimits(get()); //save
    }

    @Override
    public void removeFromList(int index) {
        super.removeFromList(index);
        Storage.setLimits(get()); //save
    }

    //EDIT LIST
}
