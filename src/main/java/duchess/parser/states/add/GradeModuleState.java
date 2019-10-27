package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.AddGradeCommand;
import duchess.logic.commands.Command;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.Util;
import duchess.parser.states.DefaultState;
import duchess.parser.states.ParserState;

import java.util.Map;

/**
 * Handles the parsing of grades associated with modules.
 */
public class GradeModuleState implements ParserState {
    private final Parser parser;
    private final String description;
    private final int marks;
    private final int maxMarks;
    private final int weightage;

    /**
     * Initializes a state to parse module associated with a grade.
     *
     * @param parser the main parser instance
     * @param description the grade description
     * @param marks the marks obtained
     * @param maxMarks the maximum marks obtainable
     * @param weightage the weightage of the grade
     */
    public GradeModuleState(Parser parser, String description, int marks, int maxMarks, int weightage) {
        this.parser = parser;
        this.description = description;
        this.marks = marks;
        this.maxMarks = maxMarks;
        this.weightage = weightage;
    }

    @Override
    public Command parse(String input) throws DuchessException {
        Map<String, String> parameters = Util.parameterizeWithoutCommand(input);
        return processModule(parameters.get("general"));
    }

    @Override
    public Command continueParsing(Map<String, String> parameters) {
        return processModule(parameters.get("module"));
    }

    private Command processModule(String moduleCode) {
        if (moduleCode == null) {
            return new DisplayCommand(String.format(Parser.GRADE_MODULE_PROMPT, description));
        } else {
            this.parser.setParserState(new DefaultState(parser));
            return new AddGradeCommand(marks, maxMarks, weightage, description, moduleCode);
        }
    }
}
