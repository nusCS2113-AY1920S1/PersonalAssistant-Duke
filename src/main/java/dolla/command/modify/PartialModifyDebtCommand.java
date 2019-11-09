package dolla.command.modify;

import dolla.model.Debt;
import dolla.model.DollaData;
import dolla.model.Record;
import dolla.ui.ModifyUi;

import java.time.LocalDate;

public class PartialModifyDebtCommand extends ModifyDebtCommand {

    public PartialModifyDebtCommand(int recordNum, String type, String name,
                                    double amount, String description, LocalDate date) {
        this.index = recordNum - 1;
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    @Override
    public void execute(DollaData dollaData) {
        dollaData.prepForModify(MODE_DEBT, index);

        if (isIndexInList(dollaData.getRecordListObj(MODE_DEBT), MODE_DEBT)) {
            updateUndoState(dollaData);
            Record originalDebt = dollaData.getRecordFromList(MODE_DEBT, index);
            overwriteComponents(originalDebt);
            Debt newDebt = new Debt(type, name, amount, description, date);
            dollaData.modifyRecordList(newDebt);
            ModifyUi.echoModifyRecord(newDebt);
            dollaData.updateMode(MODE_DEBT);
        } else {
            return;
        }
    }

    private void overwriteComponents(Record ogDebt) {
        if (type == null) {
            type = ogDebt.getType();
        }
        if (name == null) {
            name = ogDebt.getName();
        }
        if (amount == -1) {
            amount = ogDebt.getAmount();
        }
        if (description == null) {
            description = ogDebt.getDescription();
        }
        if (date == null) {
            date = ogDebt.getDate();
        }
    }

    @Override
    public String getCommandInfo() {
        return null;
    }

}
