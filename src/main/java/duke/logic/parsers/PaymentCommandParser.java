package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.PaymentCommand;

public class PaymentCommandParser implements ParserInterface <PaymentCommand> {

    @Override
    public PaymentCommand parse(String userInput) throws DukeException {
        if (userInput.trim().length() == 0) {
            throw new DukeException("Please enter the amount to be paid for today's date or" +
                    "the date and the amount to be deposited");
        }
        if (userInput.contains("/date")) {
            String paymentAmount = userInput.split("/date", 2)[0].trim();
            String dateString = userInput.split("/date", 2)[1].trim();
            return new PaymentCommand(paymentAmount, dateString);
        } else {
            return new PaymentCommand(userInput);
        }
    }
}
