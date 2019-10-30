package duchess.parser.states.add;

import duchess.logic.commands.AddEventCommand;
import duchess.logic.commands.Command;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.states.DefaultState;
import duchess.parser.states.ParserState;

import java.time.LocalDateTime;
import java.util.Map;

public class EventWeightageState extends ParserState {
    private final Parser parser;
    private final String description;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final String moduleCode;

    /**
     * Initializes a state to process module associated with an event.
     *
     * @param parser the main parser instance
     * @param description the description of the event
     * @param start the start time of the event
     * @param end the end time of the event
     */
    public EventWeightageState(
            Parser parser, String description, LocalDateTime start, LocalDateTime end, String moduleCode) {
        super("weightage");
        this.parser = parser;
        this.description = description;
        this.start = start;
        this.end = end;
        this.moduleCode = moduleCode;
    }

    @Override
    public Command process(String value, Map<String, String> parameters) {
        if (value == null) {
            return new DisplayCommand(Parser.TASK_WEIGHTAGE_PROMPT);
        }

        if (value.equalsIgnoreCase("nil")) {
            parser.setParserState(new DefaultState(parser));
            return new AddEventCommand(description, end, start, moduleCode);
        }

        int weightage;
        try {
            weightage = Integer.parseInt(value);
            if (weightage > 100 || weightage < 0) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            return new DisplayCommand(Parser.TASK_INVALID_WEIGHTAGE);
        }

        parser.setParserState(new DefaultState(parser));
        return new AddEventCommand(description, end, start, moduleCode, weightage);
    }
}
