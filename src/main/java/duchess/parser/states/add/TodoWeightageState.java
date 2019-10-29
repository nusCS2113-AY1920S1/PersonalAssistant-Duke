package duchess.parser.states.add;

import duchess.logic.commands.AddTodoCommand;
import duchess.logic.commands.Command;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.states.DefaultState;
import duchess.parser.states.ParserState;

import java.util.Map;

public class TodoWeightageState extends ParserState {
    private Parser parser;
    private String description;
    private String moduleCode;

    /**
     * Initializes a state to process weightage of grade associated with todo.
     *
     * @param parser the main parser instance
     * @param description the description of the task
     * @param moduleCode the code of the associated module
     */
    public TodoWeightageState(Parser parser, String description, String moduleCode) {
        super("weightage");
        this.parser = parser;
        this.description = description;
        this.moduleCode = moduleCode;
    }

    @Override
    public Command process(String value, Map<String, String> parameters) {
        if (value == null) {
            return new DisplayCommand(Parser.TODO_WEIGHTAGE_PROMPT);
        }

        if (value.equalsIgnoreCase("nil")) {
            parser.setParserState(new DefaultState(parser));
            return new AddTodoCommand(description, moduleCode);
        }

        int weightage;
        try {
            weightage = Integer.parseInt(value);
            if (weightage > 100 || weightage < 0) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            return new DisplayCommand(Parser.TODO_INVALID_WEIGHTAGE);
        }

        parser.setParserState(new DefaultState(parser));
        return new AddTodoCommand(description, moduleCode, weightage);

    }
}
