package owlmoney.logic.command;

import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public abstract class Command {
    protected boolean isExit = false;
    public abstract boolean execute(Profile profile, Ui ui);
}
