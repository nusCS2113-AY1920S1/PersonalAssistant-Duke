package duke.command;

// enum for generating hashmap of strings to commands

// TODO: Write different commands for different contexts

/**
 * Maintains the associations between command keywords and commands (e.g. "list" -> ListCommand). For use in parsing
 * user input.
 */
public enum Cmd {

    // TODO: adapt getCommand to take in a Context as an argument and use it to disambiguate commands
    DOCTOR("doctor") {
        public Command getCommand() {
            return new DoctorCommand();
        }
    },
    LIST("list") {
        public Command getCommand() {
            return new ListCommand();
        }
    },
    BYE("bye") {
        public Command getCommand() {
            return new ByeCommand();
        }
    },
    DONE("done") {
        public Command getCommand() {
            return new DoneCommand();
        }
    },
    DEL("delete") {
        public Command getCommand() {
            return new DeleteCommand();
        }
    },
    FIND("find") {
        public Command getCommand() {
            return new FindCommand();
        }
    },
    NEW("new") {
        public Command getCommand() {
            return new NewPatientCommand();
        }
    };

    private final String cmdStr;

    /**
     * Creates the Cmd enum instance and associates the specified keyword with it.
     *
     * @param cmdStr The keyword to be associated with the specified task type.
     */
    Cmd(final String cmdStr) {
        this.cmdStr = cmdStr;
    }

    @Override
    public String toString() {
        return cmdStr;
    }

    /**
     * Creates an empty (all parameters null) new Command of the specified type, to be loaded with data using its
     * parse method.
     *
     * @return A new Command of the specified type.
     */
    public abstract Command getCommand();
}
