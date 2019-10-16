package duke.command;

import duke.DukeCore;

/**
 * Stub Command for testing new Parser.
 */

public class DoctorCommand extends ArgCommand {

    @Override
    public void execute(DukeCore core) {
        core.ui.print("Argument: " + getArg() + System.lineSeparator() + "Switch: " +
                getSwitchVal("switch"));
    }

    @Override
    ArgCommandSpec getSpec() {
        return DoctorCommandSpec.getSpec();
    };
}