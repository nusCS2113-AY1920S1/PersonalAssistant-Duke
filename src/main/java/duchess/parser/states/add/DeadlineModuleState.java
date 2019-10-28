package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.AddDeadlineCommand;
import duchess.logic.commands.Command;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.states.DefaultState;
import duchess.parser.states.ParserState;

import java.time.LocalDateTime;
import java.util.Map;

public class DeadlineModuleState extends ParserState {
    private Parser parser;
    private String description;
    private LocalDateTime due;

    /**
     * Initializes a state to handle parsing of modules associated with deadlines.
     *
     * @param parser the main parser instance
     * @param description description of deadline
     * @param due deadline due time
     */
    public DeadlineModuleState(Parser parser, String description, LocalDateTime due) {
        super("module");
        this.parser = parser;
        this.description = description;
        this.due = due;
    }

    @Override
    public Command process(String module, Map<String, String> parameters) throws DuchessException {
        if (module == null) {
            String prompt = String.format(Parser.TASK_MODULE_PROMPT, description);
            return new DisplayCommand(prompt);
        }

        this.parser.setParserState(new DefaultState(parser));

        if (module.equalsIgnoreCase("nil")) {
            return new AddDeadlineCommand(description, due);
        }

        return new AddDeadlineCommand(description, due, module);
    }
}
