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
 * Handles the parsing of module names.
 */
public class ModuleNameState extends ParserState {
    private final Parser parser;

    public ModuleNameState(Parser parser) {
        super("name");
        this.parser = parser;
    }

    @Override
    public Command process(String name, Map<String, String> parameters) throws DuchessException {
        Optional<ParserState> nextState = Optional.ofNullable(name)
                .map(moduleName -> new ModuleCodeState(parser, moduleName));

        if (nextState.isPresent()) {
            return parser.setParserState(nextState.get()).continueParsing(parameters);
        } else {
            return new DisplayCommand(Parser.MODULE_NAME_PROMPT);
        }
    }
}
