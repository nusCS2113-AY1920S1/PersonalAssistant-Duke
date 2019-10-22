//@@author A0171206R

package wallet.logic.parser;

import wallet.logic.command.ReminderCommand;

/**
 * The RemindCommand class
 * changes user input String into appropriate parameters.
 */
public class ReminderCommandParser implements Parser<ReminderCommand> {

    @Override
    public ReminderCommand parse(String input) {
        if (!"".equals(input)) {
            return new ReminderCommand(input);
        }

        System.out.println(ReminderCommand.MESSAGE_USAGE);
        return null;
    }
}
