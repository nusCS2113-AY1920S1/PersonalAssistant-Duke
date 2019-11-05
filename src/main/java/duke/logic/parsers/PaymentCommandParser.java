package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.AddTransactionCommand;
import duke.model.wallet.Payment;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Parser class to handle addition of a payment transaction to the wallet.
 */
public class PaymentCommandParser implements ParserInterface<AddTransactionCommand> {
    LocalDate localDate = LocalDate.now();

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
            if (!amountAndDate[1].isBlank()) {
                try {
                    localDate = LocalDate.parse(amountAndDate[1], dateFormat);
                } catch (DateTimeParseException e) {
                    return new AddTransactionCommand(true, "Unable to parse " + amountAndDate[1] + " as a date. "
                            + "Please follow DD/MM/YYYY format.");
                }
            }

            return new AddTransactionCommand(new Payment(amountAndDate[0], localDate));
        } catch (DukeException e) {
            return new AddTransactionCommand(false, e.getMessage());
        }
    }
}
