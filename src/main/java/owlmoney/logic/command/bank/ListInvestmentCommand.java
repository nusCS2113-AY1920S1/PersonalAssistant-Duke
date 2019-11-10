package owlmoney.logic.command.bank;

import static owlmoney.commons.log.LogsCenter.getLogger;

import java.util.logging.Logger;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * Executes ListInvestmentCommand to list investment objects.
 */
public class ListInvestmentCommand extends Command {
    private static final String INVESTMENT_BANK_TYPE = "investment";
    private static final Logger logger = getLogger(ListInvestmentCommand.class);

    /**
     * Executes the function to list savings in the profile.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws BankException If there are no bank account of specified type.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) throws BankException {
        profile.profileListBanks(INVESTMENT_BANK_TYPE, ui);
        logger.info("Successful execution of ListInvestmentCommand");
        return this.isExit;
    }
}
