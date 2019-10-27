package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.Util;
import duchess.parser.states.ParserState;

import java.util.Map;
import java.util.Optional;

/**
 * Handles the parsing of grade weightage.
 */
public class GradeWeightageState implements ParserState {
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
        this.parser = parser;
        this.description = description;
        this.marks = marks;
        this.maxMarks = maxMarks;
    }

    @Override
    public Command parse(String input) throws DuchessException {
        Map<String, String> parameters = Util.parameterizeWithoutCommand(input);
        return processWeightage(parameters.get("general"), parameters);
    }

    @Override
    public Command continueParsing(Map<String, String> parameters) throws DuchessException {
        return processWeightage(parameters.get("weightage"), parameters);
    }

    private Command processWeightage(String weightage, Map<String, String> parameters) throws DuchessException {
        Optional<ParserState> nextState = Optional.ofNullable(weightage)
                .map(weight -> {
                    try {
                        return Integer.parseInt(weight);
                    } catch (NumberFormatException e) {
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
