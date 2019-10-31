package cube.logic.parser;

import cube.logic.command.BatchCommand;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;

public class BatchCommandParser implements ParserPrototype<BatchCommand> {

    public BatchCommand parse(String[] args) throws ParserException {
        String[] params = new String[]{"-o","-i"};

        if(ParserUtil.hasInvalidParameters(args,params)){
            throw new ParserException(ParserErrorMessage.INVALID_PARAMETER);
        }
        if(ParserUtil.hasRepetitiveParameters(args)){
            throw new ParserException(ParserErrorMessage.REPETITIVE_PARAMETER);
        }
        if (args.length < 3) {
            throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
        }

        switch (args[1]) {
        case "-o":
            return new BatchCommand(args[2], "EXPORT");
        case "-i":
            return new BatchCommand(args[2], "IMPORT");
        }
        return null;
    }
}
