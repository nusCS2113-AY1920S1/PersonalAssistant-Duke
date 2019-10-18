package dolla.command;

import dolla.DollaData;
import dolla.Ui;
import dolla.task.Debt;

public class AddDebtsCommand extends Command {

    private String type;
    private String name;
    private double amount;
    private String description;

    public AddDebtsCommand(String type, String name, double amount, String description) {
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.description = description;
    }

    @Override
    public void execute(DollaData dollaData) {
        Debt newDebt = new Debt(type, name, amount,description);
        dollaData.addToLogList("debt", newDebt);
        Ui.echoAddDebt(newDebt);
    }
}
