package owlmoney.logic.command.goals;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.Bank;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.goals.exception.GoalsException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

import java.util.Date;

/**
 * Executes EditGoalsCommand to edit goal object.
 */
public class EditGoalsCommand extends Command {

    private final String name;
    private final String amount;
    private final String newName;
    private final Date date;
    private final String savingName;
    private Bank savingAccount;
    private boolean markDone;

    /**
     * Creates an instance of EditGoalsCommand.
     *
     * @param name    Name of goal object.
     * @param amount  Income of new goal object.
     * @param date    Initial amount of new goal object.
     * @param newName New name of the goal object
     */
    public EditGoalsCommand(String name, String amount, Date date, String newName, String savingName, boolean markDone) {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.newName = newName;
        this.savingName = savingName;
        this.markDone = markDone;
    }

    /**
     * Executes the function to edit goals in the profile.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws GoalsException If goal does not exists / invalid parameters provided
     */
    @Override
    public boolean execute(Profile profile, Ui ui) throws GoalsException, BankException {
        if (!(savingName.isEmpty() || savingName.isBlank())) {
            savingAccount = profile.profileGetSavingAccount(savingName);
        }
        profile.profileEditGoals(name, amount, date, newName, savingAccount, markDone, ui);
        return this.isExit;
    }
}
