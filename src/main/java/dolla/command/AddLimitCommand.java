package dolla.command;

import dolla.DollaData;
import dolla.command.action.state.LimitState;
import dolla.command.action.Redo;
import dolla.command.action.state.UndoStateList;
import dolla.task.Limit;
import dolla.task.LimitList;
import dolla.ui.LimitUi;

/**
 * AddLimitCommand is used to create a new Limit entity.
 */
//@@author Weng-Kexin
public class AddLimitCommand extends Command {

    private String type;
    private double amount;
    private String duration;
    private static final String mode = MODE_LIMIT;

    /**
     * Instantiates a new AddLimitCommand.
     * @param type type of limit
     * @param amount amount of limit
     * @param duration duration of limit
     */
    public AddLimitCommand(String type, double amount, String duration) {
        this.type = type;
        this.amount = amount;
        this.duration = duration;
    }

    @Override
    public void execute(DollaData dollaData) {
        Limit newLimit = new Limit(type, amount, duration);

        LimitList limitList = (LimitList) dollaData.getRecordListObj(mode);
        UndoStateList.addState(new LimitState(limitList.get()), mode);///////////////////////////////////////
        Redo.clearRedoState(mode);
        //todo: need to add budget and show and deduct money every time there is an expense entry
        int duplicateLimitIndex = limitList.findExistingRecordIndex(dollaData, newLimit, mode);
        if (recordDoesNotExist(duplicateLimitIndex)) {
            dollaData.addToRecordList(mode, newLimit);
            LimitUi.echoAddRecord(newLimit);
        } else {
            Limit existingLimit = (Limit) limitList.getFromList(duplicateLimitIndex);
            LimitUi.existingLimitPrinter(existingLimit);
        }
    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}