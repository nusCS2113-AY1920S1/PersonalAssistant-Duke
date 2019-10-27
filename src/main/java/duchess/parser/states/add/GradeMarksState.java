package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.Util;
import duchess.parser.states.ParserState;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Handles the parsing of grade marks.
 */
public class GradeMarksState implements ParserState {
    private final Parser parser;
    private final String description;

    /**
     * Initializes a state to parse the marks.
     *
     * @param parser the main parser instance
     * @param description the grade description
     */

    public GradeMarksState(Parser parser, String description) {
        this.parser = parser;
        this.description = description;
    }

    @Override
    public Command parse(String input) throws DuchessException {
        Map<String, String> parameters = Util.parameterizeWithoutCommand(input);
        return processMarks(parameters.get("general"), parameters);
    }

    @Override
    public Command continueParsing(Map<String, String> parameters) throws DuchessException {
        return processMarks(parameters.get("marks"), parameters);
    }

    private Command processMarks(String score, Map<String, String> parameters) throws DuchessException {
        Optional<ParserState> nextState = Optional.ofNullable(score)
                .map(marks -> {
                    try {
                        List<String> marksList = Arrays.asList(marks.split("/"));
                        int marksObtained = Integer.parseInt(marksList.get(0));
                        int maxMarks = Integer.parseInt(marksList.get(1));
                        return new int[]{marksObtained, maxMarks};
                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        return null;
                    }
                })
                .map(marks -> new GradeWeightageState(parser, description, marks[0], marks[1]));

        if (nextState.isPresent()) {
            return parser.setParserState(nextState.get()).continueParsing(parameters);
        } else {
            return new DisplayCommand(String.format(Parser.GRADE_MARKS_PROMPT, description));
        }
    }
}
