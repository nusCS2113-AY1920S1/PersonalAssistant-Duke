package dolla.command.action.state;

import dolla.model.Record;

import java.util.ArrayList;

//@@author yetong1895
public class LimitState extends State {

    /**
     * This method will set the limitState in State class to be the input param.
     * @param limitState The limitState to be set in State class.
     */
    public LimitState(ArrayList<Record> limitState) {
        this.limitState = new ArrayList<>(limitState);
    }

}
