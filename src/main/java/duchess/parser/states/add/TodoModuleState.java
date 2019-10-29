package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.AddTodoCommand;
import duchess.logic.commands.Command;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.states.DefaultState;
import duchess.parser.states.ParserState;

import java.util.Map;

public class TodoModuleState extends ParserState {
    private Parser parser;
    private String description;

    /**
     * Initializes a state to process modules associated with to-dos.
     *
     * @param parser the main parser instance
     * @param description the to-to description
     */
    public TodoModuleState(Parser parser, String description) {
        super("module");
        this.parser = parser;
        this.description = description;
    }

    @Override
    public Command process(String value, Map<String, String> parameters) throws DuchessException {
        if (value == null) {
            String str = String.format(Parser.TASK_MODULE_PROMPT, description);
            return new DisplayCommand(str);
        }

        if (value.equalsIgnoreCase("nil")) {
            parser.setParserState(new DefaultState(parser));
            return new AddTodoCommand(description);
        }

        return parser.setParserState(new TodoWeightageState(parser, description, value)).continueParsing(parameters);

    }
}
