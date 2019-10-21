package dolla.command;

import dolla.DollaData;
import dolla.Ui;
import dolla.action.undo;
import dolla.task.Debt;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AddDebtsCommand extends Command {

    private String type;
    private String name;
    private double amount;
    private String description;
    private LocalDate date;
    private int prevPosition;
    public AddDebtsCommand(String type, String name, double amount,
                           String description, LocalDate date, int prePosition) { //prePosition is -1 by default
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.prevPosition = prePosition;
    }

    @Override
    public void execute(DollaData dollaData) {
        Debt newDebt = new Debt(type, name, amount, description, date);
        if(prevPosition != -1) { //an undo input
            dollaData.addToPrevPosition("debt", newDebt, prevPosition);
            prevPosition = -1;
        } else { //normal input
            dollaData.addToLogList("debt", newDebt);
            index = dollaData.getLogList("debt").size();
            undo.removeCommand("debt",index);
        }
        Ui.echoAddDebt(newDebt);
    }
}
