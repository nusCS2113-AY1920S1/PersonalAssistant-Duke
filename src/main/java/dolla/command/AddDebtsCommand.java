package dolla.command;

import dolla.command.action.Undo;
import dolla.model.DollaData;

import dolla.command.action.Redo;
import dolla.model.DebtList;
import dolla.model.Record;
import dolla.ui.Ui;
import dolla.model.Debt;

import java.time.LocalDate;

public class AddDebtsCommand extends Command {

    private String type;
    private String name;
    private double amount;
    private String description;
    private LocalDate date;
    private static final String mode = MODE_DEBT;

    /**
     * Instantiates AddDebtsCommand.
     * @param type type of debt.
     * @param name name of debtor.
     * @param amount amount of debt.
     * @param description description of debt.
     * @param date date of debt.
     */
    public AddDebtsCommand(String type, String name, double amount,
                           String description, LocalDate date) {
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    @Override
    public void execute(DollaData dollaData) {
        index = dollaData.getRecordListObj(mode).size();
        DebtList debtList = (DebtList) dollaData.getRecordListObj(mode);
        Undo.addToStateList(mode,debtList.get());
        Redo.clearRedoState(mode);
        Debt newDebt = new Debt(type, name, amount, description, date);
        int duplicateDebtIndex = debtList.findExistingRecordIndex(newDebt);
        if (recordDoesNotExist(duplicateDebtIndex)) {
            dollaData.addToRecordList(mode, newDebt);
            Ui.echoAddRecord(newDebt);
        } else {
            Record existingDebt = debtList.getFromList(duplicateDebtIndex);
            Ui.existingRecordPrinter(existingDebt, mode);
        }
    }

    @Override
    public String getCommandInfo() {
        return type + " " + name + " " + amount + " " + description + " " + date;
    }
}
