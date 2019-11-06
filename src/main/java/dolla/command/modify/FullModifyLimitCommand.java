package dolla.command.modify;

import dolla.DollaData;
import dolla.command.Command;
import dolla.task.Limit;
import dolla.task.LimitList;
import dolla.task.Record;
import dolla.ui.LimitUi;
import dolla.ui.ModifyUi;

//@@author omupenguin
public class FullModifyLimitCommand extends Command {

    private String type;
    private double amount;
    private String duration;
    private static final String mode = MODE_LIMIT;

    /**
     * Instantiates a new FullModifyLimitCommand.
     * @param type type of limit
     * @param amount amount of limit
     * @param duration duration of limit
     */
    public FullModifyLimitCommand(String type, double amount, String duration) {
        this.type = type;
        this.amount = amount;
        this.duration = duration;
    }

    @Override
    public void execute(DollaData dollaData) {
        Limit newLimit = new Limit(type, amount, duration);
        LimitList limitList = (LimitList) dollaData.getRecordListObj(mode);
        int duplicateLimitIndex = limitList.findExistingRecordIndex(dollaData, newLimit, mode);
        int indexToModify = dollaData.getModifyIndex();

        if (isNewLimitValid(duplicateLimitIndex, indexToModify)) {
            dollaData.modifyRecordList(newLimit);
            ModifyUi.echoModifyRecord(newLimit);
            dollaData.updateMode("limit");
        } else {
            Record existingLimit = limitList.getFromList(duplicateLimitIndex);
            LimitUi.existingRecordPrinter(existingLimit, mode);
        }
    }

    @Override
    public String getCommandInfo() {
        return null;
    }

    /**
     * Returns true if the new limit does not result in having more than one instance of
     * 'daily', 'weekly' and 'monthly' duration-based limits.
     * @param duplicateLimitIndex the index where the duplicate limit of the same duration is found
     * @param indexToModify the index of the limit in dollaData to be modified
     * @return true if either recordDoesNotExist() or isSameIndex is true
     */
    private boolean isNewLimitValid(int duplicateLimitIndex, int indexToModify) {
        return (recordDoesNotExist(duplicateLimitIndex) || isSameIndex(duplicateLimitIndex, indexToModify));
    }

    /**
     * Returns true if the specified duplicateLimitIndex and indexToEdit are the same.
     * @param duplicateLimitIndex the index where the duplicate limit of the same duration is found
     * @param indexToModify the index of the limit in dollaData to be modified
     * @return true if the 2 specified ints are the same.
     */
    private boolean isSameIndex(int duplicateLimitIndex, int indexToModify) {
        return (duplicateLimitIndex == indexToModify);
    }

}
