package dolla.command.modify;

import dolla.model.DollaData;
import dolla.model.Limit;
import dolla.model.LimitList;
import dolla.model.Record;
import dolla.ui.LimitUi;
import dolla.ui.ModifyUi;

//@@author omupenguin
public class FullModifyLimitCommand extends ModifyLimitCommand {

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
            updateUndoState(dollaData);
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



}
