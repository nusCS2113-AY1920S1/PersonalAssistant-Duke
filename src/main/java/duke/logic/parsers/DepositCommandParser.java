package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.AddTransactionCommand;
import duke.model.wallet.Deposit;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Parser class to handle deposits to the wallet.
 */
public class DepositCommandParser implements ParserInterface<AddTransactionCommand> {
    LocalDate localDate = LocalDate.now();

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
            if (!amountAndDate[1].isBlank()) {
                try {
                    localDate = LocalDate.parse(amountAndDate[1], dateFormat);
                } catch (DateTimeParseException e) {
                    return new AddTransactionCommand(true, "Unable to parse " + amountAndDate[1] + " as a date. "
                            + "Please follow DD/MM/YYYY format.");
                }
            }

            return new AddTransactionCommand(new Deposit(amountAndDate[0], localDate));
        } catch (DukeException e) {
            return new AddTransactionCommand(false,"Please enter the amount to deposit "
                    + "for today's date or date and amount to be deposited");
        }
    }
}
