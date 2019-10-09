package owlmoney.logic.command.transaction;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class EditExpenditureCommand extends Command {

    private final String accName;
    private final String amount;
    private final String date;
    private final String description;
    private final String category;
    private final int index;

    //working code but haven't parse date
    public EditExpenditureCommand(String name, String amount, String date, String description, String category
            , int index) {
        this.accName = name;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
        this.index = index;
    }

    public boolean execute(Profile profile, Ui ui) {
        profile.editExpenditure(index, accName, description, amount, date, category, ui);
        return this.isExit;
    }
}
