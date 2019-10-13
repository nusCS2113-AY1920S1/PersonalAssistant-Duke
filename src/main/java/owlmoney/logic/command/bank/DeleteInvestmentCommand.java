package owlmoney.logic.command.bank;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class DeleteInvestmentCommand extends Command {
    private final String bankName;
    private static final String INVESTMENT = "investment";

    /**
     * Creates an instance of DeleteInvestmentCommand.
     *
     * @param bankName Bank name to be deleted.
     */
    public DeleteInvestmentCommand(String bankName) {
        this.bankName = bankName;
    }

    /**
     * Executes the function to delete an investment account from the profile.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) throws BankException {
        profile.deleteBank(this.bankName, INVESTMENT, ui);
        return this.isExit;
    }
}
