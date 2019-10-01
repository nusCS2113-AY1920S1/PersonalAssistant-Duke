package owlmoney.logic.command;

import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public abstract class OwlMoneyCommand {
    public abstract void execute(Profile profile, Ui ui);
}
