package owlmoney.logic.command.transaction;

import java.util.Date;

import owlmoney.logic.command.Command;
import owlmoney.model.transaction.Expenditure;
import owlmoney.model.transaction.Transaction;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class AddExpenditureCommand extends Command {

    private final String accName;
    private final double amount;
    private final Date date;
    private final String description;
    private final String category;

    //working code but haven't parse date
    public AddExpenditureCommand(String name, double amount, Date date, String description, String category) {
        this.accName = name;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
    }

    public boolean execute(Profile profile, Ui ui) {
        Transaction newExpenditure = new Expenditure(this.description, this.amount, this.date, this.category);
        profile.addNewExpenditure(accName, newExpenditure, ui);
        return this.isExit;
    }
}
