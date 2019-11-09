package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.Util;
import duchess.parser.states.ParserState;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static duchess.parser.Parser.ADD_TYPE_PROMPT;

/**
 * Handles the parsing of entity type to add.
 */
public class AddState extends ParserState {
    private final Parser parser;

    public AddState(Parser parser) {
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
                    if (type.charAt(0) == 'm') {
                        return new ModuleNameState(parser);
                    } else if (type.charAt(0) == 'e') {
                        return new EventDescriptionState(parser);
                    } else if (type.charAt(0) == 'g') {
                        return new GradeDescriptionState(parser);
                    } else if (type.charAt(0) == 'd') {
                        return new DeadlineDescriptionState(parser);
                    } else if (type.charAt(0) == 't') {
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
        List<String> validValues = List.of("module", "deadline", "todo", "event", "grade", "m", "d", "t", "e", "g");
        if (validValues.indexOf(type) < 0) {
            return null;
        }
        return type;
    }
}
