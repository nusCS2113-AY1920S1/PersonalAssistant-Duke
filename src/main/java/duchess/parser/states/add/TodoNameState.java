package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.states.ParserState;

import java.util.Map;

public class TodoNameState extends ParserState {
    private Parser parser;

    public TodoNameState(Parser parser) {
        super("name");
        this.parser = parser;
    }

    @Override
    public Command process(String value, Map<String, String> parameters) throws DuchessException {
        if (value == null) {
            return new DisplayCommand(Parser.TODO_DESCRIPTION_PROMPT);
        }

        return parser.setParserState(new TodoModuleState(parser, value)).continueParsing(parameters);
    }
}
