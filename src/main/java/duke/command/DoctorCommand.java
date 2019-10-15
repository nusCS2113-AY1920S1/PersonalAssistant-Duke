package duke.command;

import duke.DukeCore;

import java.util.Map;

/**
 * Stub Command for testing new Parser.
 */
public class DoctorCommand extends ArgCommand {

    static {
        cmdArgLevel = ArgLevel.OPTIONAL;
        emptyArgMsg = "You didn't tell me what to do!";
        switches = Map.ofEntries(
                Map.entry("switch", ArgLevel.OPTIONAL)
        );
    }

    @Override
    public void execute(DukeCore core) {
        core.ui.print("Argument: " + arg + System.lineSeparator() + "Switch: " + switchVals.get("switch"));
    }
}