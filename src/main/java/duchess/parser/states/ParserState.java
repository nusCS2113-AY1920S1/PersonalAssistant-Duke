package duchess.parser.states;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;
import duchess.parser.Parser;
import duchess.parser.Util;

import java.util.Map;

public abstract class ParserState {
    private final String key;

    public ParserState() {
        this.key = "general";
    }

    public ParserState(String key) {
        this.key = key;
    }

    public Command parse(String input) throws DuchessException {
        Map<String, String> parameters = Util.parameterizeWithoutCommand(input);
        return process(parameters.get("general"), parameters);
    }

    public Command continueParsing(Map<String, String> parameters) throws DuchessException {
        return process(parameters.get(key), parameters);
    }

    public Command process(String value, Map<String, String> parameters) throws DuchessException {
        throw new DuchessException(Parser.PARSING_ERROR_MESSAGE);
    }
}
