package dolla.command;

import dolla.model.DollaData;

/**
 * A command that is generated if a proper command could not be produced (as a result of invalid inputs).
 * Since the command is invalid, nothing should happen when execute() runs.
 */
public class ErrorCommand extends Command {

    @Override
    public void execute(DollaData dollaData) {
    }

    @Override
    public String getCommandInfo() {
        String errorCommand = "ErrorCommand";
        return errorCommand;
    }
}
