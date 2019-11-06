package dolla.command.action.state;

import dolla.model.Record;

import java.util.ArrayList;

//@@author yetong1895
public class DebtState extends State {

    /**
     * This method will set the debtState in State class to be the input param.
     * @param debtState The debtState to be set in State class.
     */
    public DebtState(ArrayList<Record> debtState) {
        this.debtState = new ArrayList<>(debtState);
    }
}
