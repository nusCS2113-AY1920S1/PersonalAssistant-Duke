package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.AddTransactionCommand;
import duke.model.Payment;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

/**
 * Parser class to handle addition of a payment transaction to the wallet.
 */
public class PaymentCommandParser implements ParserInterface<AddTransactionCommand> {

    /**
     * Parse user input and return PaymentCommandParser.
     * @param userInput String input by user
     * @return <code>AddTransactionCommand</code> Command object encapsulating the details of the transaction
     * @throws DukeException If the userInput cannot be parsed
     */
    @Override
    public AddTransactionCommand parse(String userInput) throws DukeException {
        InputValidator.validate(userInput);
        String[] amountAndDate = ArgumentSplitter.splitArguments(userInput, "/date");
        return new AddTransactionCommand(new Payment(amountAndDate[0], amountAndDate[1]));
    }
}
