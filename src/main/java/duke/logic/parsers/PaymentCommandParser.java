package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.AddTransactionCommand;
import duke.model.wallet.Payment;

/**
 * Parser class to handle addition of a payment transaction to the wallet.
 */
public class PaymentCommandParser implements ParserInterface<AddTransactionCommand> {

    /**
     * Parse user input and return PaymentCommandParser.
     * @param userInputStr String input by user
     * @return <code>AddTransactionCommand</code> Command object encapsulating the details of the transaction
     */
    @Override
    public AddTransactionCommand parse(String userInputStr) throws DukeException {
        try {
            InputValidator.validate(userInputStr);
            String[] amountAndDate = ArgumentSplitter.splitArguments(userInputStr, "/date");
            return new AddTransactionCommand(new Payment(amountAndDate[0], amountAndDate[1]));
        } catch (DukeException e) {
            return new AddTransactionCommand(false, e.getMessage());
        }
    }
}
