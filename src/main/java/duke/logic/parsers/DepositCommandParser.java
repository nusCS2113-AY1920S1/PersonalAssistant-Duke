package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.AddTransactionCommand;
import duke.model.wallet.Deposit;

/**
 * Parser class to handle deposits to the wallet.
 */
public class DepositCommandParser implements ParserInterface<AddTransactionCommand> {

    /**
     * Parse user input and return AddTransactionCommand.
     * @param userInputStr String input by user.
     * @return <code>AddTransactionCommand</code> Command object encapsulating the amount to be deposited
     */
    @Override

    public AddTransactionCommand parse(String userInputStr) {
        try {
            InputValidator.validate(userInputStr);
            String[] amountAndDate = ArgumentSplitter.splitArguments(userInputStr, "/date");
            return new AddTransactionCommand(new Deposit(amountAndDate[0], amountAndDate[1]));
        } catch (DukeException e) {
            return new AddTransactionCommand(false,"Please enter the amount to deposit " +
                    "for today's date or date and amount to be deposited");
        }
    }
}
