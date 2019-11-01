package dolla.command;

import dolla.DollaData;
import dolla.ModeStringList;
import dolla.action.*;
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
    private String mode;

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
        mode = ModeStringList.MODE_DEBT;
    }

    @Override
    public void execute(DollaData dollaData) {
        Debt newDebt = new Debt(type, name, amount, description, date);
        index = dollaData.getRecordList(mode).size();
        DebtList debtList = (DebtList) dollaData.getRecordList(mode);
        StateList.addState(new DebtState(debtList.get()), mode);///////////////////////////////////////
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
