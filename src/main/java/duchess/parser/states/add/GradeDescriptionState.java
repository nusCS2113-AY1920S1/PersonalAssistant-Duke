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
 * Handles the parsing of grade descriptions.
 */
public class GradeDescriptionState implements ParserState {
    private final Parser parser;

    /**
     * Initializes a state to parse the grade description.
     *
     * @param parser the main parser instance
     */
    public GradeDescriptionState(Parser parser) {
        this.parser = parser;
    }

    @Override
    public Command parse(String input) throws DuchessException {
        Map<String, String> parameters = Util.parameterizeWithoutCommand(input);
        return processDescription(parameters.get("general"), parameters);
    }

    @Override
    public Command continueParsing(Map<String, String> parameters) throws DuchessException {
        return processDescription(parameters.get("name"), parameters);
    }

    private Command processDescription(String description, Map<String, String> parameters) throws DuchessException {
        Optional<ParserState> nextState = Optional.ofNullable(description)
                .map(desc -> new GradeMarksState(parser, desc));

        if (nextState.isPresent()) {
            return parser.setParserState(nextState.get()).continueParsing(parameters);
        } else {
            return new DisplayCommand(Parser.GRADE_DESCRIPTION_PROMPT);
        }
    }
}
