package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.Util;
import duchess.parser.states.ParserState;

import java.util.Map;
import java.util.Optional;

/**
 * Handles the parsing of event descriptions.
 */
public class EventDescriptionState implements ParserState {
    private final Parser parser;

    public EventDescriptionState(Parser parser) {
        this.parser = parser;
    }

    @Override
    public Command parse(String input) throws DuchessException {
        Map<String, String> parameters = Util.parameterizeWithoutCommand(input);
        return processDescription(parameters.get("general"), parameters);
    }

    @Override
    public Command continueParsing(Map<String, String> parameters) throws DuchessException {
        return processDescription(parameters.get("name"), parameters);
    }

    private Command processDescription(String description, Map<String, String> parameters) throws DuchessException {
        Optional<ParserState> nextState = Optional.ofNullable(description)
                .map(desc -> new EventFromState(parser, desc));

        if (nextState.isPresent()) {
            return parser.setParserState(nextState.get()).continueParsing(parameters);
        } else {
            return new DisplayCommand(Parser.EVENT_DESCRIPTION_PROMPT);
        }
    }
}
