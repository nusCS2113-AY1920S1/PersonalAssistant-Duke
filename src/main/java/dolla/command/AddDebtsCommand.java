package dolla.command;

import dolla.DollaData;

import dolla.action.Redo;
import dolla.action.state.DebtState;
import dolla.action.state.UndoStateList;
import dolla.task.DebtList;
import dolla.ui.DebtUi;
import dolla.ui.Ui;
import dolla.task.Debt;

import java.time.LocalDate;

public class AddDebtsCommand extends Command {

    private String type;
    private String name;
    private double amount;
    private String description;
    private LocalDate date;
    private String tagName;
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
                           String description, LocalDate date, String tagName) {
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.tagName = tagName;
    }

    @Override
    public void execute(DollaData dollaData) {
        index = dollaData.getRecordListObj(mode).size();
        DebtList debtList = (DebtList) dollaData.getRecordListObj(mode);
        UndoStateList.addState(new DebtState(debtList.get()), mode);
        Redo.clearRedoState(mode);
        Debt newDebt = new Debt(type, name, amount, description, date, tagName);
        int duplicateDebtIndex = debtList.findExistingRecordIndex(dollaData, newDebt, mode);
        if (recordDoesNotExist(duplicateDebtIndex)) {
            dollaData.addToRecordList(mode, newDebt);
            Ui.echoAddRecord(newDebt);
        } else {
            Debt existingDebt = (Debt) debtList.getFromList(duplicateDebtIndex);
            DebtUi.existingDebtPrinter(existingDebt);
        }
    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}
