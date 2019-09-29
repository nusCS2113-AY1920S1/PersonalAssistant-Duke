package duke.util;

import duke.command.Command;
import duke.exceptions.DukeException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class ParserWrapper {

    // Testing Natty Wrapper
    private NattyWrapper natty;
    private HashSet<String> dateCommandKeys;
    private final String[] values = {"event", "deadline", "fixedDuration"};

    /**
     * Constructor for parser wrapper class.
     */
    public ParserWrapper() {
        natty = new NattyWrapper();
        dateCommandKeys = setDateCommandKeys();
    }

    private HashSet<String> setDateCommandKeys() {
        return new HashSet<>(Arrays.asList(values));
    }

    /**
     * Parsing date arguments.
     * @param input User input.
     * @return Command class based on user input.
     * @throws DukeException error based on user input.
     */
    public Command parseDateArgs(String input) throws DukeException {

        Command ret = DukeParser.parse(input);
        return ret;
    }



}
