package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.log.Log;
import duchess.logic.commands.Command;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.states.ParserState;

import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the parsing of module names.
 */
public class ModuleNameState extends ParserState {
    private static final String NAME_KEYWORD = "name";
    private static final String NAME_STATE_LOG_MSG = "In module name state now";
    private final Parser parser;
    private final Logger logger = Log.getLogger();

    /**
     * Initializes a state to parse module name.
     *
     * @param parser the parser instance
     */
    public ModuleNameState(Parser parser) {
        super(NAME_KEYWORD);
        logger.log(Level.INFO, NAME_STATE_LOG_MSG);
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
