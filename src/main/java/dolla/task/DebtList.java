package dolla.task;

import dolla.Storage;
import dolla.task.Log;

import java.util.ArrayList;

public class DebtList extends LogList {

    public DebtList(ArrayList<Log> importDebtList) {
        super(importDebtList);
    }

    @Override
    public void add(Log newLog) {
        super.add(newLog);
        Storage.setDebts(get()); //save
    }

    @Override
    public void removeFromList(int index) {
        super.removeFromList(index);
        Storage.setDebts(get()); //save
    }
}
