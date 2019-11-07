package dolla.command;

import dolla.DollaData;
import dolla.exception.DollaException;
import dolla.task.Bill;
import dolla.ui.DebtUi;

import java.util.ArrayList;

//@@author tatayu
public class AddBillCommand extends Command {

    private String type;
    private int people;
    private double amount;
    private ArrayList<String> nameList;

    /**
     * Instantiates AddBillCommand.
     * @param type type of debt (is bill in this case).
     * @param people number of people.
     * @param amount the total amount.
     * @param nameList the namelist.
     */
    public AddBillCommand(String type, int people, double amount, ArrayList<String> nameList) {
        this.type = type;
        this.people = people;
        this.amount = amount;
        this.nameList = nameList;
    }

    @Override
    public void execute(DollaData dollaData) throws DollaException {
        Bill newBill = new Bill(type, people, amount, nameList);
        dollaData.addBillToRecordList(newBill);
        DebtUi.printAverageAmount(people, amount, nameList);
    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}
