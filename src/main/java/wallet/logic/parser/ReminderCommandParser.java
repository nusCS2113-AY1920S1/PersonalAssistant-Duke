package wallet.logic.parser;

import wallet.logic.command.ReminderCommand;

/**
 * The ListCommandParser class helps to
 * change user input String into appropriate parameters.
 */
public class ReminderCommandParser implements Parser<ReminderCommand> {

    @Override
    public ReminderCommand parse(String input) {
        if (input != "") {
            return new ReminderCommand(input);
        }

        System.out.println(ReminderCommand.MESSAGE_USAGE);
        return null;
    }
}
