package dolla.action.state;

import dolla.task.Record;

import java.util.ArrayList;

//@@author yetong1895
public class EntryState extends State {

    /**
     * This method will set the entryState in State class to be the input param.
     * @param entryState The entryState to be set in State class.
     */
    public EntryState(ArrayList<Record> entryState) {
        this.entryState = new ArrayList<>(entryState);
    }
}
