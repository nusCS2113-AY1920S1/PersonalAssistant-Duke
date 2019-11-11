package duchess.parser;

import duchess.exceptions.DuchessException;
import duchess.log.Log;
import duchess.logic.commands.Command;
import duchess.parser.states.DefaultState;
import duchess.parser.states.ParserState;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses user input.
 */
public class Parser {
    /**
     * Used for list command.
     */
    public static final String TASKS_KEYWORD = "tasks";
    public static final String MODULES_KEYWORD = "modules";
    public static final String GRADES_KEYWORD = "grades";
    public static final String TASK_KEYWORD = "task";
    public static final String MODULE_KEYWORD = "module";
    public static final String LESSON_KEYWORD = "lesson";
    public static final String GRADE_KEYWORD = "grade";
    public static final String DISPLAY_KEYWORD = "display";
    public static final String EXPORT_KEYWORD = "export";
    public static final String WEEK_KEYWORD = "week";
    public static final String DAY_KEYWORD = "day";
    public static final String MARKS_KEYWORD = "marks";
    public static final String DEADLINE_KEYWORD = "deadline";
    public static final String TODO_KEYWORD = "todo";
    public static final String EVENT_KEYWORD = "event";

    /**
     * Usage for commands.
     */
    public static final String LIST_USAGE =
            "Usage: list (tasks | modules | grades /module <module_code>)";
    public static final String LESSON_USAGE =
            "Usage: lesson /code <module_code> /type <lesson_type> "
                    + "\n    /time <start_date> <start_time> /to <end_date> <end_time>";
    public static final String DELETE_USAGE =
            "Usage: delete (task | module | grade /module <module_code>) /no <number>"
                    + "\n    Usage: delete (lesson) /type <lesson_type> /code <module_code>";
    public static final String CALENDAR_USAGE = "calendar (display | export) /date <date> /view (day | week)";
    public static final String CALENDAR_DATE_USAGE = "Please input a date in the current formal academic year.";
    public static final String CALENDAR_VIEW_USAGE = "Please input either the day or week view.";
    public static final String DONE_USAGE = "Usage: done (task | grade /module <module_code> "
            + "\n    /marks <marks>) /no <number>";

    /**
     * Prompts.
     */
    public static final String ADD_TYPE_PROMPT =
            "What do you want to add? (module | deadline | todo | event | grade)";
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
    public static final String TASK_MODULE_PROMPT =
            "What module is %s for? (enter 'nil' to skip)";
    public static final String GRADE_MODULE_PROMPT =
            "What module is %s for? (e.g CS1231)";
    public static final String GRADE_MARKS_PROMPT =
            "What's your score for %s? (e.g. 20/30)";
    public static final String GRADE_DESCRIPTION_PROMPT =
            "What's the name of the assessment? (e.g midterm)";
    public static final String GRADE_WEIGHTAGE_PROMPT =
            "What's the weightage of %s?";
    public static final String DEADLINE_DESCRIPTION_PROMPT =
            "What's the name of the deadline?";
    public static final String DEADLINE_DEADLINE_PROMPT =
            "When is %s due?";
    public static final String TODO_DESCRIPTION_PROMPT =
            "What's the to-do about?";
    public static final String TASK_WEIGHTAGE_PROMPT =
            "If this is an assessment, what's its weightage? (enter 'nil' to skip)";
    public static final String TASK_INVALID_WEIGHTAGE =
            "Please enter a weightage between 0 to 100. (enter 'nil' to skip)";
    private static final String PARSING_LOG_MSG = "Parsing ";
    private static final String CONTINUING_PARSING_LOG_MSG = "Continuing parsing ";

    private final Logger logger;

    /**
     * Error messages.
     */
    public static final String PARSING_ERROR_MESSAGE =
            "An unexpected error occurred while processing your command.";

    private ParserState parserState;

    /**
     * Initializes the duchess parser with a default logger.
     */
    public Parser() {
        this.parserState = new DefaultState(this);
        this.logger = Log.getLogger();
    }

    /**
     * Returns the command to execute in response to the user input.
     *
     * @param input the raw user input
     * @return the command to execute
     * @throws DuchessException if the user input is invalid
     */
    public Command parse(String input) throws DuchessException {
        logger.log(Level.INFO, PARSING_LOG_MSG + input);

        if (input.equals("exit") || input.equals("bye")) {
            this.parserState = new DefaultState(this);
        }
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
        logger.log(Level.INFO, CONTINUING_PARSING_LOG_MSG + parameters);

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
