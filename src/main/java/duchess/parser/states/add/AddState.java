package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.log.Log;
import duchess.logic.commands.Command;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.Util;
import duchess.parser.states.ParserState;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static duchess.parser.Parser.ADD_TYPE_PROMPT;
import static duchess.parser.Parser.DEADLINE_KEYWORD;
import static duchess.parser.Parser.EVENT_KEYWORD;
import static duchess.parser.Parser.GRADE_KEYWORD;
import static duchess.parser.Parser.MODULE_KEYWORD;
import static duchess.parser.Parser.TODO_KEYWORD;

/**
 * Handles the parsing of entity type to add.
 */
public class AddState extends ParserState {
    private static final String ADD_STATE_LOG_MSG = "In add state now";
    private final Parser parser;
    private final Logger logger = Log.getLogger();

    public AddState(Parser parser) {
        logger.log(Level.INFO, ADD_STATE_LOG_MSG);
        this.parser = parser;
    }

    /**
     * Returns the command to execute next if the user enters the partial command.
     *
     * @param input user input
     * @return the command to execute next
     * @throws DuchessException if the user input is invalid
     */
    @Override
    public Command parse(String input) throws DuchessException {
        Map<String, String> parameters = Util.parameterizeWithoutCommand(input);
        return processParameters(parameters);
    }

    /**
     * Returns the command to execute next if the user enters the full command.
     * @param parameters the parameterized user input
     * @return the command to execute next
     * @throws DuchessException if the user input is invalid
     */
    @Override
    public Command continueParsing(Map<String, String> parameters) throws DuchessException {
        return processParameters(parameters);
    }

    /**
     * Determine the next state and command based on user input.
     * @param parameters the parameterized user input
     * @return the command to execute next
     * @throws DuchessException if the user input is invalid
     */
    private Command processParameters(Map<String, String> parameters) throws DuchessException {
        Optional<ParserState> nextState = Optional.ofNullable(parameters.get("general"))
                .map(String::toLowerCase)
                .map(this::validateInclusion)
                .map(type -> {
                    if (type.equalsIgnoreCase(MODULE_KEYWORD)) {
                        return new ModuleNameState(parser);
                    } else if (type.equalsIgnoreCase(EVENT_KEYWORD)) {
                        return new EventDescriptionState(parser);
                    } else if (type.equalsIgnoreCase(GRADE_KEYWORD)) {
                        return new GradeDescriptionState(parser);
                    } else if (type.equalsIgnoreCase(DEADLINE_KEYWORD)) {
                        return new DeadlineDescriptionState(parser);
                    } else if (type.equalsIgnoreCase(TODO_KEYWORD)) {
                        return new TodoNameState(parser);
                    } else {
                        return null;
                    }
                });

        if (nextState.isPresent()) {
            return parser.setParserState(nextState.get()).continueParsing(parameters);
        } else {
            return new DisplayCommand(ADD_TYPE_PROMPT);
        }
    }

    private String validateInclusion(String type) {
        List<String> validValues = List.of(
                MODULE_KEYWORD,
                DEADLINE_KEYWORD,
                TODO_KEYWORD,
                EVENT_KEYWORD,
                GRADE_KEYWORD);
        if (validValues.indexOf(type) < 0) {
            return null;
        }
        return type;
    }
}
