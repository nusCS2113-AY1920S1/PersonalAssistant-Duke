package owlmoney.logic.command.transaction;

import java.util.Date;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.model.transaction.Deposit;
import owlmoney.model.transaction.Transaction;
import owlmoney.ui.Ui;

public class AddDepositCommand extends Command {

    private final String accName;
    private final double amount;
    private final Date date;
    private final String description;
    private final String category = "deposit";

    //working code but haven't parse date
    public AddDepositCommand(String name, double amount, Date date, String description) {
        this.accName = name;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public void execute(Profile profile, Ui ui) {
        Transaction newDeposit = new Deposit(this.description, this.amount, this.date, this.category);
        profile.addNewDeposit(accName, newDeposit, ui);
    }
}
