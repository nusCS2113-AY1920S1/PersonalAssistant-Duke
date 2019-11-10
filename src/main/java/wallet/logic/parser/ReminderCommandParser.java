//@@author A0171206R

package wallet.logic.parser;

import wallet.logic.command.ReminderCommand;
import wallet.ui.Ui;

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

        Ui.printError(ReminderCommand.MESSAGE_USAGE);
        return null;
    }
}
