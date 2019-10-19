package wallet.logic.parser;

import wallet.logic.command.ReminderCommand;

/**
 * The RemindCommand class
 * changes user input String into appropriate parameters.
 */
public class ReminderCommandParser implements Parser<ReminderCommand> {

    @Override
    public ReminderCommand parse(String input) {
        if (!input.equals("")) {
            return new ReminderCommand(input);
        }

        System.out.println(ReminderCommand.MESSAGE_USAGE);
        return null;
    }
}
