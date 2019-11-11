package duchess.parser.states.add;

import duchess.logic.commands.AddGradeCommand;
import duchess.logic.commands.Command;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.states.DefaultState;
import duchess.parser.states.ParserState;

import java.util.Map;

/**
 * Handles the parsing of grades associated with modules.
 */
public class GradeModuleState extends ParserState {
    private final Parser parser;
    private final String description;
    private final double marks;
    private final double maxMarks;
    private final double weightage;

    /**
     * Initializes a state to parse module associated with a grade.
     *
     * @param parser the main parser instance
     * @param description the grade description
     * @param marks the marks obtained
     * @param maxMarks the maximum marks obtainable
     * @param weightage the weightage of the grade
     */
    public GradeModuleState(Parser parser, String description, double marks, double maxMarks, double weightage) {
        super("module");
        this.parser = parser;
        this.description = description;
        this.marks = marks;
        this.maxMarks = maxMarks;
        this.weightage = weightage;
    }

    @Override
    public Command process(String moduleCode, Map<String, String> parameters) {
        if (moduleCode == null) {
            return new DisplayCommand(String.format(Parser.GRADE_MODULE_PROMPT, description));
        } else {
            this.parser.setParserState(new DefaultState(parser));
            return new AddGradeCommand(marks, maxMarks, weightage, description, moduleCode);
        }
    }
}
