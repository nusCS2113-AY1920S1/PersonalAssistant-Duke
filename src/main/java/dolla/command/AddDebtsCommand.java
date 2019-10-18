package dolla.command;

import dolla.DollaData;
import dolla.Ui;
import dolla.task.Debt;

import java.time.LocalDateTime;

public class AddDebtsCommand extends Command {

    private String type;
    private String name;
    private double amount;
    private String description;
    private LocalDateTime date;

    public AddDebtsCommand(String type, String name, double amount, String description, LocalDateTime date) {
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    @Override
    public void execute(DollaData dollaData) {
        Debt newDebt = new Debt(type, name, amount,description, date);
        dollaData.addToLogList("debt", newDebt);
        Ui.echoAddDebt(newDebt);
    }
}
