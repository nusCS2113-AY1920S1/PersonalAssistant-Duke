package dolla.command.modify;

import dolla.model.DollaData;
import dolla.command.Command;
import dolla.model.Debt;
import dolla.ui.ModifyUi;

import java.time.LocalDate;

//@@author omupenguin
public class FullModifyDebtCommand extends Command {

    private String type;
    private String name;
    private double amount;
    private String description;
    private LocalDate date;
    private int prevPosition;
    //private String mode = "debt";

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
        this.prevPosition = prevPosition;
    }

    @Override
    public void execute(DollaData dollaData) {
        Debt newDebt = new Debt(type, name, amount, description, date);
        dollaData.modifyRecordList(newDebt);
        ModifyUi.echoModifyRecord(newDebt);
        dollaData.updateMode("debt");
    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}
