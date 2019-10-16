package dolla;

import dolla.task.DebtList;
import dolla.task.EntryList;
import dolla.task.LogList;

import java.util.ArrayList;

public class DollaData {

    private String mode = "dolla";
    public EntryList entryList; // TODO: Find out alternatives to using a public variable
    public DebtList debtList;


    public DollaData() {
        this.entryList = new EntryList(new ArrayList<Log>());
        // this.entryList = new EntryList(importEntryList()); TODO: Import from save file
        this.debtList = new DebtList(new ArrayList<Log>());
    }

    public EntryList getEntryList() {
        return entryList;
    }

    public DebtList getDebtList() {
        return debtList;
    }

    public String getMode() {
        return mode;
    }

    public void updateMode(String newMode) {
        mode = newMode;
    }
}
