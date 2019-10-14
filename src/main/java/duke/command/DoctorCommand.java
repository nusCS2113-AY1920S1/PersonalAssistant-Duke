package duke.command;

import duke.DukeCore;

/**
 * Stub Command for testing new Parser.
 */
public class DoctorCommand extends ArgCommand {

    /**
     * Constructor for DoctorCommand command, specifying 1 possible switch.
     */
    public DoctorCommand() {
        cmdArgLevel = ArgLevel.OPTIONAL;
        emptyArgMsg = "You didn't tell me what to do!";
        switches.put("switch", ArgLevel.OPTIONAL);
    }

    @Override
    public void execute(DukeCore core) {
        core.ui.print("Argument: " + arg + System.lineSeparator() + "Switch: " + switchVals.get("switch"));
    }
}