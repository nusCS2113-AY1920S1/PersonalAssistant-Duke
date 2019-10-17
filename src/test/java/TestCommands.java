import duke.command.Command;
import duke.command.Commands;
import duke.command.DoctorCommand;

public class TestCommands extends Commands {
    @Override
    public Command getCommand(String cmdStr) {
        if ("doctor".equals(cmdStr)) {
            return new DoctorCommand();
        }
        return null;
    }
}
