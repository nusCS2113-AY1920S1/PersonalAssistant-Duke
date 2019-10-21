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
 * Handles the parsing of event start time.
 */
public class EventFromState implements ParserState {
    private final Parser parser;
    private final String description;

    public EventFromState(Parser parser, String description) {
        this.parser = parser;
        this.description = description;
    }

    @Override
    public Command parse(String input) throws DuchessException {
        Map<String, String> parameters = Util.parameterizeWithoutCommand(input);
        return processFromDate(parameters.get("general"), parameters);
    }

    @Override
    public Command continueParsing(Map<String, String> parameters) throws DuchessException {
        return processFromDate(parameters.get("from"), parameters);
    }

    private Command processFromDate(String from, Map<String, String> parameters) throws DuchessException {
        Optional<ParserState> nextState = Optional.ofNullable(from)
                .map(date -> {
                    try {
                        return Util.parseDateTime(date);
                    } catch (DuchessException e) {
                        return null;
                    }
                })
                .map(date -> new EventToState(parser, description, date));

        if (nextState.isPresent()) {
            return parser.setParserState(nextState.get()).continueParsing(parameters);
        } else {
            return new DisplayCommand(String.format(Parser.EVENT_START_PROMPT, description));
        }
    }
}
