package duke.util;

import duke.command.Command;
import duke.exceptions.DukeException;

public class ParserWrapper {

    // Testing Natty Wrapper
    private NattyWrapper natty;

    /**
     * Constructor for parser wrapper class.
     */
    public ParserWrapper() {
        natty = new NattyWrapper();
    }

    public Command parse(String input) throws DukeException {
        Command ret = DukeParser.parse(input);
        return ret;
    }



}
