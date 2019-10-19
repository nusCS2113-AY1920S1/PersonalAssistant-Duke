package wallet.logic.parser;

import wallet.logic.LogicManager;
import wallet.logic.command.DoneCommand;

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
    public DoneCommand parse(String input) throws ParseException {
        String[] arguments = input.split(" ", 2);

        int id = Integer.parseInt(arguments[1]);
        LogicManager.getCommandHistory().add("done " + arguments[0] + " " + arguments[1]);
        return new DoneCommand(id);
    }
}
