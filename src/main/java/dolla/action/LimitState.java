package dolla.action;

import dolla.task.Record;

import java.util.ArrayList;

public class LimitState extends State {
    public LimitState(ArrayList<Record> limitState) {
        this.limitState = new ArrayList<>(limitState);
    }

}
