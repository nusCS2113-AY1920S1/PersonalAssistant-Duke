package duke.command;

// enum for generating hashmap of strings to commands

// TODO: Write different commands for different contexts

/**
 * Maintains the associations between command keywords and commands (e.g. "list" -> ListCommand). For use in parsing
 * user input.
 */
public enum Cmd {
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
    RECUR("recurring") {
        public Command getCommand() {
            return new NewRecurringTaskCommand();
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
    VIEW("view") {
        public Command getCommand() {
            return new ViewCommand();
        }
    },
    SNOOZE("snooze") {
        public Command getCommand() {
            return new SnoozeCommand();
        }
    },
    REMIND("remind") {
        public Command getCommand() {
            return new NewReminderCommand();
        }
    },
    FORP("fixedduration") {
        public Command getCommand() {
            return new NewFixedDurationCommand();
        }
    },
    BETWN("doin") {
        @Override
        public Command getCommand() {
            return new NewDoWithinPeriodCommand();
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
