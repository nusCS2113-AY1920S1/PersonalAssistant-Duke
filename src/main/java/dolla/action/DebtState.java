package dolla.action;

import dolla.task.Record;

import java.util.ArrayList;

public class DebtState extends State {

    public DebtState(ArrayList<Record> debtState) {
        this.debtState = new ArrayList<>(debtState);
    }
}
