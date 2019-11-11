package cube.logic.parser;

import cube.logic.command.DeletePromotionCommand;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;

import java.util.Arrays;

/**
 * Parse delete promotion command.
 */
public class DeletePromotionCommandParser implements ParserPrototype<DeletePromotionCommand> {

    /**
     * Parse user delete promotion command.
     * @param args user inputs.
     * @return delete promotion command with relative parameters.
     * @throws ParserException when user input is illegal.
     */
    public DeletePromotionCommand parse(String[] args) throws ParserException {
        String[] params = new String[]{"-all","-delete"};

        if (ParserUtil.hasInvalidParameters(args, params)) {
            throw new ParserException(ParserErrorMessage.INVALID_PARAMETER);
        }
        if (ParserUtil.hasRepetitiveParameters(args)) {
            throw new ParserException(ParserErrorMessage.REPETITIVE_PARAMETER);
        }

        if (args.length == 2 && !args[1].equals("-all")) {
            throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
        }

        if (args[2].equals("-all")) {
            return new DeletePromotionCommand(String.join(" ", Arrays.copyOfRange(args,2,args.length)),"ALL");
        }

        if (!ParserUtil.isValidInteger(args[2])) {
            throw new ParserException((ParserErrorMessage.INVALID_INTEGER));
        }
        return new DeletePromotionCommand(Integer.parseInt(args[2]), "INDEX");
    }
}