//@@author A0171206R

package wallet.logic.parser;

import wallet.exception.InsufficientParameters;
import wallet.exception.WrongParameterFormat;
import wallet.logic.command.DoneCommand;
import wallet.ui.Ui;

import java.text.ParseException;

/**
 * The DoneCommandParser Class converts user String to
 * appropriate parameters.
 */
public class DoneCommandParser implements Parser<DoneCommand> {

    /**
     * Changes user input String to appropriate parameters
     * and returns a DeleteCommand object.
     *
     * @param input User input of command.
     * @return A DeleteCommand object.
     * @throws ParseException ParseException.
     */
    @Override
    public DoneCommand parse(String input) throws InsufficientParameters, ParseException {
        String[] arguments = input.split(" ");
        int id;
        if ("loan".equals(arguments[0])) {
            try {
                id = Integer.parseInt(arguments[1]);
            } catch (NumberFormatException err) {
                throw new WrongParameterFormat("You need to provide a valid ID (Number) when settling your loans!");
            }
            return new DoneCommand(id);
        } else {
            Ui.printError("DONE command can only be applied to loans!");
            return null;
        }
    }
}
