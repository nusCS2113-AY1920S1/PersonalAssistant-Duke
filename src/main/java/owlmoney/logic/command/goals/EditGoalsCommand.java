package owlmoney.logic.command.goals;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class EditGoalsCommand extends Command {

    private final String name;
    private final double amount;
    private final String new_name;
    private final String date;

    public EditGoalsCommand(String name, double amount, String date, String new_name) {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.new_name = new_name;
    }

    @Override
    public boolean execute(Profile profile, Ui ui) {
        profile.editGoals(name, amount, new_name, date, ui);
        return this.isExit;
    }
}
