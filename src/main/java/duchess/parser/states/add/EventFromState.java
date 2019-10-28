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
public class EventFromState extends ParserState {
    private final Parser parser;
    private final String description;

    /**
     * Initializes a state to parse event start time.
     *
     * @param parser the main parser instance
     * @param description the event description
     */
    public EventFromState(Parser parser, String description) {
        super("from");
        this.parser = parser;
        this.description = description;
    }

    @Override
    public Command process(String from, Map<String, String> parameters) throws DuchessException {
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
