package duke.command;

import duke.DukeCore;

/**
 * Test Command for testing new Parser
 */
public class DoctorCommand extends ArgCommand {

    public DoctorCommand() {
        emptyArgMsg = "You didn't tell me what to do!";
        switches.put("switch", ArgLevel.OPTIONAL);
    }

    @Override
    public void execute(DukeCore core) {
        core.ui.print("Argument: " + arg + System.lineSeparator() + "Switch: " + switches.get("switch"));
    }
}
