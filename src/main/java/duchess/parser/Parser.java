package duchess.parser;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;
import duchess.parser.states.DefaultState;
import duchess.parser.states.ParserState;

import java.util.Map;

/**
 * Parses user input.
 */
public class Parser {
    /**
     * Used for list command.
     */
    public static String TASKS_KEYWORD = "tasks";
    public static String MODULES_KEYWORD = "modules";

    /**
     * Usage for commands.
     */
    public static String LIST_USAGE =
            "Usage: list (tasks | modules)";

    /**
     * Prompts.
     */
    public static final String ADD_TYPE_PROMPT =
            "What do you want to add? (module | deadline | todo | event)";
    public static final String MODULE_NAME_PROMPT =
            "What's the name of the module? (e.g. Discrete Mathematics)";
    public static final String MODULE_CODE_PROMPT =
            "What's the module code for %s? (e.g. CS1231)";
    public static final String EVENT_DESCRIPTION_PROMPT =
            "What's the name of the event?";
    public static final String EVENT_START_PROMPT =
            "When does %s start? (dd/mm/yyyy hhmm)";
    public static final String EVENT_END_PROMPT =
            "When does %s end? (dd/mm/yyyy hhmm)";
    public static final String EVENT_MODULE_PROMPT =
            "What module is %s for? (enter 'nil' to skip)";

    private ParserState parserState;

    /**
     * Initializes the duchess parser.
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
     * Continues the parsing of user input from extracted parameters.
     *
     * <p>
     * This function is called when there are consecutive state transitions
     * within a single parse call.
     * </p>
     *
     * @param parameters the parameterized user input
     * @return the command to execute
     * @throws DuchessException if the user input is invalid
     */
    public Command continueParsing(Map<String, String> parameters) throws DuchessException {
        return this.parserState.continueParsing(parameters);
    }

    /**
     * Sets the duchess parser state.
     *
     * @param newState the state to set the duchess parser to
     * @return the parser itself
     */
    public Parser setParserState(ParserState newState) {
        this.parserState = newState;
        return this;
    }

    /**
     * Returns the current parser state.
     *
     * @return the parser state
     */
    public ParserState getParserState() {
        return this.parserState;
    }
}
