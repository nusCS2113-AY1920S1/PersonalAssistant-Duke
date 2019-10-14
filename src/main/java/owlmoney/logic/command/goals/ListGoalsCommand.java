package owlmoney.logic.command.goals;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class ListGoalsCommand extends Command {

    @Override
    public boolean execute(Profile profile, Ui ui) {
        profile.listGoals(ui);
        return this.isExit;
    }
}
