package owlmoney.logic.command;

import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * Command class which is abstract where various command types inherit from given that it is abstract.
 */
public abstract class Command {
    protected boolean isExit = false;

    /**
     * Abstract method where each command type implements execution code.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return True if OwlMoney should terminate after execution.
     */
    public abstract boolean execute(Profile profile, Ui ui);
}
