package owlmoney.logic.command.goals;

import owlmoney.logic.command.Command;
import owlmoney.model.goal.Goals;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class DeleteGoalsCommand extends Command {

    private final String name;
    public DeleteGoalsCommand(String name) {
        this.name = name;
    }

    @Override
    public boolean execute(Profile profile, Ui ui) {
        profile.deleteGoals(name, ui);
        return this.isExit;
    }
}
