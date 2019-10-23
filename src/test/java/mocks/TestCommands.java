package mocks;

import duke.command.Command;
import duke.command.Commands;
import duke.ui.UiContext;

public class TestCommands extends Commands {
    @Override
    public Command getCommand(String cmdStr, UiContext.Context context) {
        if ("doctor".equals(cmdStr)) {
            return new DoctorCommand();
        }
        return null;
    }
}
