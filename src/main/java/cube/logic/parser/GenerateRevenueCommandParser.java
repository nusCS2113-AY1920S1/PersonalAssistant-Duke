//@@author LL-Pengfei
package cube.logic.parser;

import cube.logic.command.GenerateRevenueCommand;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;

import java.util.Arrays;

public class GenerateRevenueCommandParser implements ParserPrototype<GenerateRevenueCommand> {
    public GenerateRevenueCommand parse(String[] args) throws ParserException {
        if (args.length < 2) {
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
