package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.states.ParserState;

import java.util.Map;
import java.util.Optional;

/**
 * Handles the parsing of grade weightage.
 */
public class GradeWeightageState extends ParserState {
    private final Parser parser;
    private final String description;
    private final int marks;
    private final int maxMarks;

    /**
     * Initializes a state to parse the weightage of the grade.
     *
     * @param parser the main parser instance
     * @param description the grade description
     * @param marks the marks obtained
     * @param maxMarks the maximum marks obtainable
     */
    public GradeWeightageState(Parser parser, String description, int marks, int maxMarks) {
        super("weightage");
        this.parser = parser;
        this.description = description;
        this.marks = marks;
        this.maxMarks = maxMarks;
    }

    @Override
    public Command process(String weightage, Map<String, String> parameters) throws DuchessException {
        Optional<ParserState> nextState = Optional.ofNullable(weightage)
                .map(weight -> {
                    try {
                        int w = Integer.parseInt(weight);
                        if (w > 100 || w < 0) {
                            throw new IllegalArgumentException();
                        }
                        return w;
                    } catch (IllegalArgumentException e) {
                        return null;
                    }
                })
                .map(weight -> new GradeModuleState(parser, description, marks, maxMarks, weight));
        if (nextState.isPresent()) {
            return parser.setParserState(nextState.get()).continueParsing(parameters);
        } else {
            return new DisplayCommand(String.format(Parser.GRADE_WEIGHTAGE_PROMPT, description));
        }
    }
}
