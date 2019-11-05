package owlmoney.logic.command.find;

import owlmoney.logic.command.Command;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * Executes FindRecurringExpenditureCommand to find recurring expenditure from savings account.
 */
public class FindRecurringExpenditureCommand extends Command {
    private final String name;
    private final String description;
    private final String category;
    private final String type;

    /**
     * Creates an instance of FindRecurringExpenditureCommand.
     *
     * @param name        The name of bank to be search.
     * @param description The description keyword to search for.
     * @param category    The category keyword to search for.
     * @param type        The type of object to search for. In this case, a search for recurring
     *                    expenditure from savings account will be performed.
     */
    public FindRecurringExpenditureCommand(String name, String description, String category, String type) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.type = type;
    }

    /**
     * Executes the function to find the recurring expenditure.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws BankException        If bank name specified does not exist.
     */
    public boolean execute(Profile profile, Ui ui) throws BankException {
        profile.findRecurringExpenditure(this.name, this.description, this.category, this.type, ui);
        return this.isExit;
    }
}
