package owlmoney.logic.command.bank;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class ListInvestmentCommand extends Command {
    private static final String INVESTMENT = "investment";

    /**
     * Executes the function to list savings in the profile.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) {
        profile.listBanks(INVESTMENT,ui);
        return this.isExit;
    }
}
