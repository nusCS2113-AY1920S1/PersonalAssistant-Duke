//@@author ZKathrynx
package cube.logic.parser;

import cube.logic.command.Command;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;

/**
 * Parse promotion command
 */
public class PromotionCommandParser {

    /**
     * Parse user promotion command.
     * @param args user inputs.
     * @return corresponding promotion command.
     * @throws ParserException when user input is illegal.
     */
    public static Command parse (String[] args) throws ParserException {
        if (args.length < 2) {
            throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
        }
        if (args[1].equals("-list")) {
            return new ListPromotionCommandParser().parse(args);
        }
        if (args[1].equals("-delete")) {
            return new DeletePromotionCommandParser().parse(args);
        }
        return new AddPromotionCommandParser().parse(args);
    }
}
