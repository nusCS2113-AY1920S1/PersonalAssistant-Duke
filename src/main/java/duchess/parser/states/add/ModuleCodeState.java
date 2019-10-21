package duchess.parser.states.add;

import duchess.logic.commands.AddModuleCommand;
import duchess.logic.commands.Command;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.Util;
import duchess.parser.states.DefaultState;
import duchess.parser.states.ParserState;

import java.util.Map;
import java.util.Optional;

/**
 * Handles the parsing of module code.
 */
public class ModuleCodeState implements ParserState {
    private final Parser parser;
    private final String moduleName;

    public ModuleCodeState(Parser parser, String moduleName) {
        this.parser = parser;
        this.moduleName = moduleName;
    }

    @Override
    public Command parse(String input) {
        Map<String, String> parameters = Util.parameterizeWithoutCommand(input);
        return processModuleCode(parameters.get("general"));
    }

    @Override
    public Command continueParsing(Map<String, String> parameters) {
        return processModuleCode(parameters.get("code"));
    }

    private Command processModuleCode(String code) {
        Optional<Command> createCommand = Optional.ofNullable(code)
                .map(moduleCode -> new AddModuleCommand(this.moduleName, moduleCode));

        createCommand.ifPresent(command -> parser.setParserState(new DefaultState(parser)));
        String prompt = String.format(Parser.MODULE_CODE_PROMPT, moduleName);
        return createCommand.orElse(new DisplayCommand(prompt));
    }
}
