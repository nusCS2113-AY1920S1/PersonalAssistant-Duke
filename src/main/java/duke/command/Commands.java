package duke.command;

// TODO: Write different commands for different contexts

import duke.ui.Context;

/**
 * Maintains the associations between command keywords and commands (e.g. "list" -> ListCommand). For use in parsing
 * user input.
 */
public class Commands {
    /**
     * Constructs and returns the command corresponding to a name provided by the user.
     *
     * @param cmdStr The user-provided name.
     * @return The newly constructed command without any parameters loaded.
     */
    public Command getCommand(String cmdStr, Context context) {
        // TODO replace with overall switch on context
        switch (cmdStr) {
        case "bye":
            return new ByeCommand();
        case "find":
            if (context == Context.PATIENT) {
                return new PatientFindCommand();
            } else {
                return null; // TODO: fill in the other contexts
            }
        case "help":
            if (context == Context.HOME) {
                return new HomeHelpCommand();
            } else {
                return null; // TODO: fill in the other contexts
            }
        case "new":
            if (context == Context.HOME) {
                return new HomeNewCommand();
            } else {
                return null; // TODO: fill in the other contexts
            }
        case "open":
            if (context == Context.HOME) {
                return new HomeOpenCommand();
            } else {
                return null; // TODO: fill in the other contexts
            }
        case "discharge":
            return new ReportCommand();
        default:
            return null;
        }
    }
}
