// @@author parvathi14

package cube.logic.parser;

import cube.logic.command.DeletePromotionCommand;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;

import java.util.Arrays;

public class DeletePromotionCommandParser implements ParserPrototype<DeletePromotionCommand>{
    public DeletePromotionCommand parse(String[] args) throws ParserException {
        String[] params = new String[]{"-i", "-all"};

        if (ParserUtil.hasInvalidParameters(args, params)) {
            throw new ParserException(ParserErrorMessage.INVALID_PARAMETER);
        }
        if(ParserUtil.hasRepetitiveParameters(args)) {
            throw new ParserException(ParserErrorMessage.REPETITIVE_PARAMETER);
        }

        if (args.length == 1 || (args.length == 2 && !args[1].equals("-all"))) {
            throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
        }


        switch (args[1]) {
            case "-i":
                if(!ParserUtil.isValidNumber(args[2])) {
                    throw new ParserException((ParserErrorMessage.INVALID_NUMBER));
                }
                return new DeletePromotionCommand(Integer.parseInt(args[2]), "INDEX");
            case "-all":
                return new DeletePromotionCommand(String.join(" ", Arrays.copyOfRange(args,2,args.length)),"ALL");
        }
        throw new ParserException(ParserErrorMessage.INVALID_COMMAND_FORMAT);
    }
}