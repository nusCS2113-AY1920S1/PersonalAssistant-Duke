package duke.command;

import duke.DukeCore;

import java.util.Map;

/**
 * Stub Command for testing new Parser.
 */
public class DoctorCommand extends ArgCommand {

    static {
        switches = Map.ofEntries(
                Map.entry("switch", ArgLevel.OPTIONAL)
        );
    }

    @Override
    public void execute(DukeCore core) {
        core.ui.print("Argument: " + super.getArg() + System.lineSeparator() + "Switch: " +
                super.getSwitchVal("switch"));
    }

    ArgLevel getCmdArgLevel() {
        return ArgLevel.OPTIONAL;
    }

    String getEmptyArgMsg() {
        return "You didn't tell me what to do!";
    }
}