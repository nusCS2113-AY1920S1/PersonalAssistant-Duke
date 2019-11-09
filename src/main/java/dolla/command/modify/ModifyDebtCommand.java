package dolla.command.modify;

import dolla.command.Command;
import dolla.command.action.Redo;
import dolla.command.action.state.DebtState;
import dolla.command.action.state.UndoStateList;
import dolla.model.DebtList;
import dolla.model.DollaData;

import java.time.LocalDate;

public abstract class ModifyDebtCommand extends Command {
    protected String type;
    protected String name;
    protected double amount;
    protected String description;
    protected LocalDate date;
    protected String tagName = "";
    protected static final String mode = MODE_DEBT;

    protected void updateUndoState(DollaData dollaData) {
        DebtList debtList = (DebtList) dollaData.getRecordListObj(mode);
        UndoStateList.addState(new DebtState(debtList.get()), mode);
        Redo.clearRedoState(mode);
    }

}
