package duchess.parser;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;
import duchess.parser.states.DefaultState;
import duchess.parser.states.ParserState;

/**
 * Parses user input.
 */
public class Parser {
    /** Used for list command. */
    public static String TASKS_KEYWORD = "tasks";
    public static String MODULES_KEYWORD = "modules";

    /** Usage for commands. */
    public static String LIST_USAGE =
            "Usage: list (tasks | modules)";

    private ParserState parserState;

    /**
     * Initializes the duchess.parser.
     */
    public Parser() {
        this.parserState = new DefaultState(this);
    }

    /**
     * Returns the command to execute in response to the user input.
     *
     * @param input the raw user input
     * @return the command to execute
     * @throws DuchessException if the user input is invalid
     */
    public Command parse(String input) throws DuchessException {
        return this.parserState.parse(input);
    }

    /**
     * Sets the duchess.parser state.
     *
     * @param newState the state to set the duchess.parser to
     */
    public void setParserState(ParserState newState) {
        this.parserState = newState;
    }
}
