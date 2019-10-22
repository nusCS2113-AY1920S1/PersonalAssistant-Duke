//@@author matthewng1996

package wallet.logic.parser;

import wallet.logic.command.SetBudgetCommand;
import wallet.model.record.Budget;

import java.text.ParseException;

public class SetBudgetParser implements Parser<SetBudgetCommand> {

    @Override
    public SetBudgetCommand parse(String input) throws ParseException {
        String[] arguments = input.split(" ", 2);
        String[] amount = arguments[0].split("\\$", 2);
        Double budgetAmount = Double.parseDouble(amount[1].trim());

        String[] monthYear = arguments[1].split("/", 2);
        int month = Integer.parseInt(monthYear[0].trim());
        int year = Integer.parseInt(monthYear[1].trim());

        Budget budget = new Budget(budgetAmount, month, year);
        return new SetBudgetCommand(budget);
    }
}
