package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.DepositCommand;

public class DepositCommandParser implements ParserInterface <DepositCommand> {

    @Override
    public DepositCommand parse(String userInput) throws DukeException {
        if (userInput.trim().length() == 0) {
            throw new DukeException("Please enter the amount to deposit for today's date or date and amount to be deposited");
        }
        if (userInput.contains("/date")) {
            String depositAmount = userInput.split("/date", 2)[0].trim();
            String dateString = userInput.split("/date", 2)[1].trim();
            return new DepositCommand(depositAmount, dateString);
        } else {
            return new DepositCommand(userInput);
        }
    }
}
