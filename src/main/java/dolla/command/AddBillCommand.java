package dolla.command;

import dolla.DollaData;
import dolla.task.Bill;
import dolla.ui.DebtUi;

//@@author tatayu
public class AddBillCommand extends Command {

    private String type;
    private int people;
    private double amount;

    public AddBillCommand(String type, int people, double amount) {
        this.type = type;
        this.people = people;
        this.amount = amount;
    }

    @Override
    public void execute(DollaData dollaData) throws Exception {
        Bill newBill = new Bill(type, people, amount);
        //dollaData.addToRecordList("debt", newBill);
        DebtUi.printAverageAmount(people, amount);
    }
}
