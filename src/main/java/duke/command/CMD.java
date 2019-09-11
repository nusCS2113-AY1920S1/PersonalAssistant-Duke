package duke.command;

// enum for generating hashmap of strings to commands

/**
 * Maintains the associations between command keywords and commands (e.g. "list" -> ListCommand). For use in parsing
 * user input.
 */
public enum CMD {
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
    TODO("todo") {
        public Command getCommand() {
            return new NewToDoCommand();
        }
    },
    EVENT("event") {
        public Command getCommand() {
            return new NewEventCommand();
        }
    },
    DLINE("deadline") {
        public Command getCommand() {
            return new NewDeadlineCommand();
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
    };

    private final String cmdStr;

    /**
     * Creates the CMD enum instance and associates the specified keyword with it.
     * @param _cmdStr The keyword to be associated with the specified task type.
     */
    CMD (final String _cmdStr) {
        cmdStr = _cmdStr;
    }

    @Override
    public String toString() {
        return cmdStr;
    }

    /**
     * Creates an empty (all parameters null) new Command of the specified type, to be loaded with data using its
     * parse method.
     * @return A new Command of the specified type.
     */
    public abstract Command getCommand();
}
