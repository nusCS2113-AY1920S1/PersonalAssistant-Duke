package dolla.command.modify;

import dolla.command.Command;
import dolla.model.DollaData;
import dolla.model.Limit;
import dolla.model.LimitList;
import dolla.model.Record;
import dolla.ui.LimitUi;
import dolla.ui.ModifyUi;

public class PartialModifyLimitCommand extends ModifyLimitCommand {

    public PartialModifyLimitCommand(int recordNum, String type, double amount, String duration) {
        this.index = recordNum - 1;
        this.type = type;
        this.amount = amount;
        this.duration = duration;
    }

    @Override
    public void execute(DollaData dollaData) {
        dollaData.prepForModify(MODE_LIMIT, index);
        if (isIndexInList(dollaData.getRecordListObj(MODE_LIMIT), MODE_LIMIT)) {
            Record originalLimit = dollaData.getRecordFromList(MODE_LIMIT, index);
            overwriteComponents(originalLimit);
            Limit newLimit = new Limit(type, amount, duration);

            LimitList limitList = (LimitList) dollaData.getRecordListObj(mode);
            int duplicateLimitIndex = limitList.findExistingRecordIndex(dollaData, newLimit, mode);
            int indexToModify = dollaData.getModifyIndex();

            if (isNewLimitValid(duplicateLimitIndex, indexToModify)) {
                dollaData.modifyRecordList(newLimit);
                ModifyUi.echoModifyRecord(newLimit);
                dollaData.updateMode(MODE_LIMIT);
            } else {
                Record existingLimit = limitList.getFromList(duplicateLimitIndex);
                LimitUi.existingRecordPrinter(existingLimit, mode);
            }
        } else {
            return;
        }
    }

    private void overwriteComponents(Record ogLimit) {
        if (type == null) {
            type = ogLimit.getType();
        }
        if (amount == -1) {
            amount = ogLimit.getAmount();
        }
        if (duration == null) {
            duration = ogLimit.getDuration();
        }
    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}
