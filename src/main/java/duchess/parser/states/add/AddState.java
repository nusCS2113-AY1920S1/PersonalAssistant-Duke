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
public class AddState implements ParserState {
    private final Parser parser;

    public AddState(Parser parser) {
        this.parser = parser;
    }

    @Override
    public Command parse(String input) throws DuchessException {
        Map<String, String> parameters = Util.parameterizeWithoutCommand(input);
        return processParameters(parameters);
    }

    @Override
    public Command continueParsing(Map<String, String> parameters) throws DuchessException {
        return processParameters(parameters);
    }

    private Command processParameters(Map<String, String> parameters) throws DuchessException {
        Optional<ParserState> nextState = Optional.ofNullable(parameters.get("general"))
                .map(String::toLowerCase)
                .map(this::validateInclusion)
                .map(type -> {
                    if (type.charAt(0) == 'm') {
                        return new ModuleNameState(parser);
                    } else if (type.charAt(0) == 'e') {
                        return new EventDescriptionState(parser);
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
