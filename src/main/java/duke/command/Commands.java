package duke.command;

// TODO: Write different commands for different contexts

/**
 * Maintains the associations between command keywords and commands (e.g. "list" -> ListCommand). For use in parsing
 * user input.
 */
public class Commands {
    // TODO: adapt getCommand to take in a Context as an argument and use it to disambiguate commands
    public Command getCommand(String cmdStr) {
        switch(cmdStr) {
        case "bye":
            return new ByeCommand();
        case "find":
            return new PatientFindCommand();
        case "new":
            return new NewPatientCommand();
        default:
            return null;
        }
    }
}
