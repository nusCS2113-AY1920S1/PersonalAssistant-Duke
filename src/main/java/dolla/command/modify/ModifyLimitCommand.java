package dolla.command.modify;

import dolla.command.Command;
import dolla.command.action.Redo;
import dolla.command.action.state.LimitState;
import dolla.command.action.state.UndoStateList;
import dolla.model.DollaData;
import dolla.model.LimitList;

public abstract class ModifyLimitCommand extends Command {

    protected String type;
    protected double amount;
    protected String duration;
    protected static final String mode = MODE_LIMIT;

    /**
     * Returns true if the new limit does not result in having more than one instance of
     * 'daily', 'weekly' and 'monthly' duration-based limits.
     * @param duplicateLimitIndex the index where the duplicate limit of the same duration is found
     * @param indexToModify the index of the limit in dollaData to be modified
     * @return true if either recordDoesNotExist() or isSameIndex is true
     */
    protected boolean isNewLimitValid(int duplicateLimitIndex, int indexToModify) {
        return (recordDoesNotExist(duplicateLimitIndex) || isSameIndex(duplicateLimitIndex, indexToModify));
    }

    /**
     * Returns true if the specified duplicateLimitIndex and indexToEdit are the same.
     * @param duplicateLimitIndex the index where the duplicate limit of the same duration is found
     * @param indexToModify the index of the limit in dollaData to be modified
     * @return true if the 2 specified ints are the same.
     */
    protected boolean isSameIndex(int duplicateLimitIndex, int indexToModify) {
        return (duplicateLimitIndex == indexToModify);
    }

    protected void updateUndoState(DollaData dollaData) {
        LimitList limitList = (LimitList) dollaData.getRecordListObj(mode);
        UndoStateList.addState(new LimitState(limitList.get()), mode);///////////////////////////////////////
        Redo.clearRedoState(mode);
    }
}
