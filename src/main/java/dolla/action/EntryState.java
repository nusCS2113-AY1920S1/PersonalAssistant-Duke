package dolla.action;

import dolla.task.Record;

import java.util.ArrayList;

public class EntryState extends State {
    public EntryState(ArrayList<Record> entryState) {
        this.entryState = new ArrayList<>(entryState);
    }
}
