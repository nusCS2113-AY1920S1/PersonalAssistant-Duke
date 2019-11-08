package dolla.command.modify;

import dolla.model.Debt;
import dolla.model.DollaData;
import dolla.ui.ModifyUi;

import java.time.LocalDate;

//@@author omupenguin
public class FullModifyDebtCommand extends ModifyDebtCommand {

    /**
     * Instantiates a new FullModifyDebtCommand.
     * @param type type of debt
     * @param name name of debtor
     * @param amount amount of debt
     * @param description description of debt
     * @param date date of debt
     */
    public FullModifyDebtCommand(String type, String name, double amount,
                                 String description, LocalDate date) {
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    @Override
    public void execute(DollaData dollaData) {
        updateUndoState(dollaData);
        Debt newDebt = new Debt(type, name, amount, description, date, tagName);
        dollaData.modifyRecordList(newDebt);
        ModifyUi.echoModifyRecord(newDebt);
        dollaData.updateMode(mode);
    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}
