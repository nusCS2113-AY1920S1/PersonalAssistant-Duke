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

public class DeadlineWeightageState extends ParserState {
    private Parser parser;
    private String description;
    private LocalDateTime due;
    private String moduleCode;

    /**
     * Initializes a state to handle parsing of weightage of grades associated with deadlines.
     *
     * @param parser the main parser instance
     * @param description description of deadline
     * @param due deadline due time
     * @param moduleCode the code of the module
     */
    public DeadlineWeightageState(Parser parser, String description, LocalDateTime due, String moduleCode) {
        super("weightage");
        this.parser = parser;
        this.description = description;
        this.due = due;
        this.moduleCode = moduleCode;
    }

    @Override
    public Command process(String value, Map<String, String> parameters) throws DuchessException {
        if (value == null) {
            return new DisplayCommand(Parser.TASK_WEIGHTAGE_PROMPT);
        }

        if (value.equalsIgnoreCase("nil")) {
            parser.setParserState(new DefaultState(parser));
            return new AddDeadlineCommand(description, due, moduleCode);
        }

        int weightage;
        try {
            weightage = Integer.parseInt(value);
            if (weightage > 100 || weightage < 0) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            return new DisplayCommand(Parser.TASK_INVALID_WEIGHTAGE);
        }

        parser.setParserState(new DefaultState(parser));
        return new AddDeadlineCommand(description, due, moduleCode, weightage);
    }
}
