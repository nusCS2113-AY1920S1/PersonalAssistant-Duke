package cube.logic.parser;

import cube.logic.command.ListPromotionCommand;
import cube.logic.parser.exception.ParserException;

/**
 * Parse list promotion command.
 */
public class ListPromotionCommandParser implements ParserPrototype<ListPromotionCommand> {

    /**
     * Parse user list promotion command.
     * @param args user inputs.
     * @return list promotion command with relative parameters.
     * @throws ParserException when user input is illegal.
     */
    public ListPromotionCommand parse(String[] args) throws ParserException {
        return new ListPromotionCommand();
    }
}
