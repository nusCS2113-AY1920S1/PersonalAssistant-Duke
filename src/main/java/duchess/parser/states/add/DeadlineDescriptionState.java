package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.states.ParserState;

import java.util.Map;

public class DeadlineDescriptionState extends ParserState {
    private Parser parser;

    public DeadlineDescriptionState(Parser parser) {
        super("name");
        this.parser = parser;
    }

    @Override
    public Command process(String description, Map<String, String> parameters) throws DuchessException {
        if (description == null) {
            return new DisplayCommand(Parser.DEADLINE_DESCRIPTION_PROMPT);
        }

        this.parser.setParserState(new DeadlineTimeState(this.parser, description));
        return this.parser.continueParsing(parameters);
    }
}
