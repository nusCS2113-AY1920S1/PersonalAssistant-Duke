package owlmoney.logic.command.goals;

import owlmoney.logic.command.Command;
import owlmoney.model.goals.exception.GoalsException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

import java.util.Date;

public class EditGoalsCommand extends Command {

    private final String name;
    private final String amount;
    private final String new_name;
    private final String date;

    public EditGoalsCommand(String name, String amount, String date, String new_name) {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.new_name = new_name;
    }

    @Override
    public boolean execute(Profile profile, Ui ui) throws GoalsException {
        profile.editGoals(name, new_name, amount, date, ui);
        return this.isExit;
    }
}
