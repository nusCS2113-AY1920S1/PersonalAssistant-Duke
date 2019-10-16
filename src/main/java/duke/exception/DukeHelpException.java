package duke.exception;

import duke.command.Command;

public class DukeHelpException extends DukeException {

    public DukeHelpException(String msg, Command command) {
        super(msg + System.lineSeparator() + System.lineSeparator() + command.getHelp());
    }
}
