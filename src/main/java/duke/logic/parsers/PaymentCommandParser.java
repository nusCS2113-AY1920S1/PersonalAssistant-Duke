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
        if (userInput.trim().length() == 0) {
            throw new DukeException("Please enter the amount to be paid for today's date or"
                    + "the date and the amount to be deposited");
        }
        if (userInput.contains("/date")) {
            String paymentAmountString = userInput.split("/date", 2)[0].trim();
            BigDecimal paymentAmount = new BigDecimal(paymentAmountString);
            String dateString = userInput.split("/date", 2)[1].trim();
            Date parsedDate;
            try {
                parsedDate = dateFormat.parse(dateString);
            } catch (ParseException e) {
                throw new DukeException("Unable to parse input" + dateString + "as a date.");
            }
            dateString = dateFormat.format(parsedDate);
            return new AddTransactionCommand(new Payment(paymentAmount, dateString));
        } else {
            BigDecimal paymentAmount = new BigDecimal(userInput);
            return new AddTransactionCommand(new Payment(paymentAmount));
        }
    }
}
