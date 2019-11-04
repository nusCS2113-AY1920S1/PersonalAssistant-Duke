package mocks;

import duke.command.Command;
import duke.command.Commands;
import duke.ui.context.Context;

public class TestCommands extends Commands {
    @Override
    public Command getCommand(String cmdStr, Context context) {
        if ("doctor".equals(cmdStr)) {
            return new DoctorCommand();
        } else if ("empty".equals(cmdStr)) {
            return new ValidEmptyCommand();
        }
        return null;
    }
}
