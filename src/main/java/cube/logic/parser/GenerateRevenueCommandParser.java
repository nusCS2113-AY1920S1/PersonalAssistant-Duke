package cube.logic.parser;

import cube.logic.command.GenerateRevenueCommand;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;

import java.util.Arrays;

public class GenerateRevenueCommandParser implements ParserPrototype<GenerateRevenueCommand> {
    public GenerateRevenueCommand parse(String[] args) throws ParserException {
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
                return new GenerateRevenueCommand("ALL");
            case "-i":
                return new GenerateRevenueCommand(Integer.parseInt(args[2]),"INDEX");
            case "-n":
                return new GenerateRevenueCommand(String.join(" ", Arrays.copyOfRange(args,2,args.length)),"NAME");
            case "-t":
                return new GenerateRevenueCommand(String.join(" ", Arrays.copyOfRange(args,2,args.length)),"TYPE");
        }
        return null;
    }
}
