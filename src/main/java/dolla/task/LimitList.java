package dolla.task;

//import dolla.Log;

import java.util.ArrayList;

/**
 * A class that contains methods regarding the Limit List.
 */
public class LimitList extends LogList {

    public LimitList(ArrayList<Log> importEntryList) {
        super(importEntryList);
    }

    /**
     * Method to search list for limits pertaining a certain input duration
     * @param list the list containing all the limits input by user
     */
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

    //ADD TO LIST
    public void addToBudget(ArrayList<Limit> list, Limit.Duration duration, double amount) {

    }

    //REMOVE FROM LIST

    //EDIT LIST
}
