package owlmoney.logic.command.goals;

import owlmoney.logic.command.Command;
import owlmoney.model.goals.Goals;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

import java.util.Date;

public class AddGoalsCommand extends Command {

    private final String name;
    private final double amount;
    private final Date date;

    public AddGoalsCommand(String name, double amount, Date date) {
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public boolean execute(Profile profile, Ui ui) {
        Goals newGoals = new Goals(this.name, this.amount, this.date);
        profile.addGoals(newGoals, ui);
        return this.isExit;
    }
}
