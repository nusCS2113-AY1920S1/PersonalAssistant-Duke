package dolla.command;

import dolla.DollaData;
import dolla.ModeStringList;
import dolla.task.DebtList;
import dolla.ui.DebtUi;
import dolla.ui.Ui;
import dolla.action.Redo;
import dolla.action.Undo;
import dolla.task.Debt;

import java.time.LocalDate;

public class AddDebtsCommand extends Command {

    private String type;
    private String name;
    private double amount;
    private String description;
    private LocalDate date;
    private int prevPosition;
    private String mode;

    /**
     * Instantiates AddDebtsCommand.
     * @param type type of debt.
     * @param name name of debtor.
     * @param amount amount of debt.
     * @param description description of debt.
     * @param date date of debt.
     * @param prevPosition previous position of a deleted input that is passed from an undo command;
     *                     -1 if the input is not from undo command.
     */
    public AddDebtsCommand(String type, String name, double amount,
                           String description, LocalDate date, int prevPosition) { //prevPosition is -1 by default
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.prevPosition = prevPosition;
        mode = ModeStringList.MODE_DEBT;
    }

    @Override
    public void execute(DollaData dollaData) {
        Debt newDebt = new Debt(type, name, amount, description, date);
        index = dollaData.getRecordList(mode).size();

        DebtList debtList = (DebtList) dollaData.getRecordList(mode);
        int duplicateDebtIndex = debtList.findExistingRecordIndex(dollaData, newDebt, mode);

        if (recordDoesNotExist(duplicateDebtIndex)) {
            if (prevPosition == -1) {
                dollaData.addToRecordList(mode, newDebt);
                Undo.removeCommand(mode, index);
                Redo.clearRedo(mode);
            } else if (prevPosition == -2) {
                dollaData.addToRecordList(mode, newDebt);
                Undo.removeCommand(mode, index);
                prevPosition = -1;
            } else { //from undo
                dollaData.addToPrevPosition(mode, newDebt, prevPosition);
                Redo.removeCommand(mode, prevPosition);
                prevPosition = -1;
            }
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