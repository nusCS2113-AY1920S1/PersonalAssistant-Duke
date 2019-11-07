//@@author LL-Pengfei
package cube.logic.parser;

import cube.logic.command.RevenueCommand;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;

import java.util.Arrays;

public class RevenueCommandParser implements ParserPrototype<RevenueCommand> {
    public RevenueCommand parse(String[] args) throws ParserException {
        String[] params = new String[]{"-i","-n","-t","-all"};

        if(ParserUtil.hasInvalidParameters(args,params)){
            throw new ParserException(ParserErrorMessage.INVALID_PARAMETER);
        }
        if(ParserUtil.hasRepetitiveParameters(args)){
            throw new ParserException(ParserErrorMessage.REPETITIVE_PARAMETER);
        }
        if (args.length < 2 || (args.length < 3 && !(args[1].equals("-all")))) {
            throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
        }

        switch (args[1]) {
            case "-all":
                return new RevenueCommand("ALL");
            case "-i":
                if(!ParserUtil.isValidNumber(args[2])){
                    throw new ParserException(ParserErrorMessage.INVALID_NUMBER);
                }
                return new RevenueCommand(Integer.parseInt(args[2]),"INDEX");
            case "-n":
                return new RevenueCommand(String.join(" ", Arrays.copyOfRange(args,2,args.length)),"NAME");
            case "-t":
                return new RevenueCommand(String.join(" ", Arrays.copyOfRange(args,2,args.length)),"TYPE");
        }
        throw new ParserException(ParserErrorMessage.INVALID_COMMAND_FORMAT);
    }
}
