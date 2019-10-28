package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.Util;
import duchess.parser.states.ParserState;

import java.time.LocalDateTime;
import java.util.Map;

public class DeadlineTimeState extends ParserState {
    private Parser parser;
    private String description;

    /**
     * Initializes a parser state to process deadline due date.
     *
     * @param parser the main parser instance
     * @param description the deadline description
     */
    public DeadlineTimeState(Parser parser, String description) {
        super("by");
        this.parser = parser;
        this.description = description;
    }

    @Override
    public Command process(String time, Map<String, String> parameters) throws DuchessException {
        if (time == null) {
            String description = String.format(Parser.DEADLINE_DEADLINE_PROMPT, this.description);
            return new DisplayCommand(description);
        }

        LocalDateTime due = Util.parseDateTime(time);
        this.parser.setParserState(new DeadlineModuleState(parser, description, due));
        return this.parser.continueParsing(parameters);
    }
}
