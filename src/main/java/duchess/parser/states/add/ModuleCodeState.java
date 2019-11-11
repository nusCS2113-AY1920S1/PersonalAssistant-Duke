package duchess.parser.states.add;

import duchess.log.Log;
import duchess.logic.commands.AddModuleCommand;
import duchess.logic.commands.Command;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.states.DefaultState;
import duchess.parser.states.ParserState;

import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the parsing of module code.
 */
public class ModuleCodeState extends ParserState {
    private final Parser parser;
    private final Logger logger = Log.getLogger();
    private final String moduleName;

    /**
     * Initializes a state to process module code.
     *
     * @param parser the main parser instance
     * @param moduleName the name of the module
     */
    public ModuleCodeState(Parser parser, String moduleName) {
        super("code");
        logger.log(Level.INFO, "In module code state now");
        this.parser = parser;
        this.moduleName = moduleName;
    }

    /**
     * Processes module code input by the user.
     *
     * @param code the module code
     * @param parameters the rest of the input
     * @return the command to execute in response
     */
    @Override
    public Command process(String code, Map<String, String> parameters) {
        Optional<Command> createCommand = Optional.ofNullable(code)
                .map(moduleCode -> new AddModuleCommand(this.moduleName, moduleCode));

        createCommand.ifPresent(command -> parser.setParserState(new DefaultState(parser)));
        String prompt = String.format(Parser.MODULE_CODE_PROMPT, moduleName);
        return createCommand.orElse(new DisplayCommand(prompt));
    }
}
