package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.AddTransactionCommand;
import duke.model.Deposit;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

/**
 * Parser class to handle deposits to the wallet.
 */
public class DepositCommandParser implements ParserInterface<AddTransactionCommand> {

    /**
     * Parse user input and return AddTransactionCommand.
     * @param userInput String input by user.
     * @return <code>AddTransactionCommand</code> Command object encapsulating the amount to be deposited
     * @throws DukeException If the userInput cannot be parsed
     */
    @Override
    public AddTransactionCommand parse(String userInput) throws DukeException {
        InputValidator.validate(userInput);
        String[] amountAndDate = ArgumentSplitter.splitArguments(userInput, "/date");
        return new AddTransactionCommand(new Deposit(amountAndDate[0], amountAndDate[1]));
    }
}
