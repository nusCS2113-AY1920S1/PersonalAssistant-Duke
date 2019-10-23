package duke.command;

// TODO: Write different commands for different contexts

import duke.ui.UiContext;

/**
 * Maintains the associations between command keywords and commands (e.g. "list" -> ListCommand). For use in parsing
 * user input.
 */
public class Commands {
    // TODO: adapt getCommand to take in a Context as an argument and use it to disambiguate commands

    /**
     * Constructs and returns the command corresponding to a name provided by the user.
     *
     * @param cmdStr The user-provided name.
     * @return The newly constructed command without any parameters loaded.
     */
    public Command getCommand(String cmdStr, UiContext.Context context) {
        switch (cmdStr) {
            case "bye":
                return new ByeCommand();
            case "find":
                if (context == UiContext.Context.PATIENT) {
                    return new PatientFindCommand();
                } else {
                    return null; // TODO: fill in the other contexts
                }
            case "new":
                if (context == UiContext.Context.HOME) {
                    return new NewPatientCommand();
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
