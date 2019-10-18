package owlmoney.logic.command.bond;

import java.util.Date;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.bond.Bond;
import owlmoney.model.bond.exception.BondException;
import owlmoney.model.card.exception.CardException;
import owlmoney.model.profile.Profile;
import owlmoney.model.transaction.Expenditure;
import owlmoney.ui.Ui;

/**
 * Executes AddBondCommand and prints the results.
 */
public class AddBondCommand extends Command {

    private final String bondName;
    private final String bankAccountName;
    private final double amount;
    private final double rate;
    private final Date date;
    private final int year;
    private final String type;
    private static final String BONDS = "bonds";

    /**
     * Creates an instance of AddBondCommand.
     *
     * @param bondName name of the bond.
     * @param amount   cost of the bond.
     * @param rate     interest rate of the bond.
     * @param date     date the bond was purchased.
     * @param year     number of years the bond holds.
     */
    public AddBondCommand(String bondName, String bankAccountName, double amount, double rate, Date date, int year,
            String type) {
        this.bondName = bondName;
        this.bankAccountName = bankAccountName;
        this.amount = amount;
        this.rate = rate;
        this.date = date;
        this.year = year;
        this.type = type;
    }

    /**
     * Executes the function to create a new investment account in the profile.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws BankException if used on savings or bank does not exist.
     * @throws BondException if there are no bonds.
     * @throws CardException if card commands are executed in bonds.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) throws BankException, BondException, CardException {
        Bond newBond = new Bond(this.bondName, this.amount, this.rate, this.date, this.year);
        Expenditure newExpenditure = new Expenditure(this.bondName, this.amount, this.date, BONDS);
        profile.profileIsBondUnique(this.bankAccountName, newBond);
        profile.profileAddNewExpenditure(this.bankAccountName, newExpenditure, ui, this.type);
        profile.profileAddNewBond(this.bankAccountName, newBond, ui);
        return this.isExit;
    }
}
