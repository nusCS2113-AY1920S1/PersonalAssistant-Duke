package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.AddTransactionCommand;
import duke.model.Deposit;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

public class DepositCommandParser implements ParserInterface <AddTransactionCommand> {

    @Override
    public AddTransactionCommand parse(String userInput) throws DukeException {
        if (userInput.trim().length() == 0) {
            throw new DukeException("Please enter the amount to deposit for today's date or date and amount to be deposited");
        }
        if (userInput.contains("/date")) {
            String depositAmountString = userInput.split("/date", 2)[0].trim();
            BigDecimal depositAmount = new BigDecimal(depositAmountString);
            String dateString = userInput.split("/date", 2)[1].trim();
            Date parsedDate;
            try {
               parsedDate = dateFormat.parse(dateString);
            } catch (ParseException e) {
                throw new DukeException("Unable to parse input" + dateString + "as a date.");
            }
            dateString = dateFormat.format(parsedDate);
            return new AddTransactionCommand(new Deposit(depositAmount, dateString));
        } else {
            BigDecimal depositAmount = new BigDecimal(userInput);
            return new AddTransactionCommand(new Deposit(depositAmount));
        }
    }
}
