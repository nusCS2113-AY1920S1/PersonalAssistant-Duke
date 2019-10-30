package dolla.command;

import dolla.DollaData;
import dolla.task.Debt;
import dolla.ui.Ui;

import java.time.LocalDate;

//@@author omupenguin
public class ModifyDebtCommand extends Command {

    private String type;
    private String name;
    private double amount;
    private String description;
    private LocalDate date;
    private int prevPosition;
    private String mode = "debt";

    /**
     * Instantiates a new ModifyDebtCommand.
     * @param type type of debt
     * @param name name of debtor
     * @param amount amount of debt
     * @param description description of debt
     * @param date date of debt
     */
    public ModifyDebtCommand(String type, String name, double amount,
                             String description, LocalDate date) {
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.prevPosition = prevPosition;
    }

    @Override
    public void execute(DollaData dollaData) {
        Debt newDebt = new Debt(type, name, amount, description, date);
        dollaData.modifyRecordList(newDebt);
        Ui.echoAddRecord(newDebt);
        dollaData.updateMode("debt");
    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}
