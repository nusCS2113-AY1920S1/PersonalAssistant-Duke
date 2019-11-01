package dolla.action;

import dolla.task.Entry;
import dolla.task.Record;

import java.util.ArrayList;

public class State {
    private ArrayList<Record> entryState = new ArrayList<>();
    private ArrayList<Record> debtState = new ArrayList<>();
    private ArrayList<Record> limitState = new ArrayList<>();
    private ArrayList<Record> shortcutState = new ArrayList<>();

    public void setEntryState(ArrayList<Record> entryState) {
        this.entryState = entryState;
    }

    public void setDebtState(ArrayList<Record> debtState) {
        this.debtState = debtState;
    }

    public void setLimitState(ArrayList<Record> limitState) {
        this.limitState = limitState;
    }

    public void setShortcutState(ArrayList<Record> shortcutState) {
        this.shortcutState = shortcutState;
    }











}
