package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.Util;
import duchess.parser.states.ParserState;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

/**
 * Handles the parsing of event end time.
 */
public class EventToState extends ParserState {
    private final Parser parser;
    private final String description;
    private final LocalDateTime start;

    /**
     * Initializes a state to process ending time of events.
     *
     * @param parser the main parser instance
     * @param description the description of the event
     * @param start the start time of the event
     */
    public EventToState(Parser parser, String description, LocalDateTime start) {
        super("to");
        this.parser = parser;
        this.description = description;
        this.start = start;
    }

    @Override
    public Command process(String to, Map<String, String> parameters) throws DuchessException {
        Optional<ParserState> nextState = Optional.ofNullable(to)
                .map(date -> {
                    try {
                        return Util.parseDateTime(date);
                    } catch (DuchessException e) {
                        return null;
                    }
                })
                .map(date -> new EventModuleState(parser, description, start, date));

        if (nextState.isPresent()) {
            return parser.setParserState(nextState.get()).continueParsing(parameters);
        } else {
            return new DisplayCommand(String.format(Parser.EVENT_END_PROMPT, description));
        }
    }
}
