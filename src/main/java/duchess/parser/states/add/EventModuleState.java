package duchess.parser.states.add;

import duchess.logic.commands.AddEventCommand;
import duchess.logic.commands.Command;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.Util;
import duchess.parser.states.DefaultState;
import duchess.parser.states.ParserState;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Handles the parsing of events associated with modules.
 */
public class EventModuleState extends ParserState {
    private final Parser parser;
    private final String description;
    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Initializes a state to process module associated with an event.
     *
     * @param parser the main parser instance
     * @param description the description of the event
     * @param start the start time of the event
     * @param end the end time of the event
     */
    public EventModuleState(Parser parser, String description, LocalDateTime start, LocalDateTime end) {
        super("module");
        this.parser = parser;
        this.description = description;
        this.start = start;
        this.end = end;
    }

    @Override
    public Command process(String module, Map<String, String> parameters) {
        if (module == null) {
            return new DisplayCommand(String.format(Parser.TASK_MODULE_PROMPT, description));
        } else if (module.equals("nil")) {
            this.parser.setParserState(new DefaultState(parser));
            return new AddEventCommand(description, end, start);
        } else {
            this.parser.setParserState(new DefaultState(parser));
            return new AddEventCommand(description, end, start, module);
        }
    }
}
