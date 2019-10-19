package dolla.command;

import dolla.DollaData;

/**
 * duke.command.ErrorCommand is a type of command that is generated if a proper command
 * could not be produced. IE. as a result of invalid inputs.
 */
public class ErrorCommand extends Command {

    @Override
    public void execute(DollaData dollaData) {
        return; // Don't do anything
    }
}
