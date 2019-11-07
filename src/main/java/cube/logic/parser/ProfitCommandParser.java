//@@author LL-Pengfei
package cube.logic.parser;

import cube.logic.command.ProfitCommand;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;

import java.util.Arrays;
import java.util.Date;

public class ProfitCommandParser implements ParserPrototype<ProfitCommand> {
    public ProfitCommand parse(String[] args) throws ParserException {
        if (args.length < 6) {
            throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
        }
        if (!args[5].equals("-all") && args.length < 7) {
            throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
        }
        Date date1 = ParserUtil.parseStringToDate(args[2]);
        Date date2 = ParserUtil.parseStringToDate(args[4]);
        switch (args[5]) {
            case "-all":
                return new ProfitCommand(date1, date2, "ALL");
            case "-i":
                return new ProfitCommand(date1, date2, Integer.parseInt(args[6]),"INDEX");
            case "-n":
                return new ProfitCommand(date1, date2, String.join(" ", Arrays.copyOfRange(args,6,args.length)),"NAME");
            case "-t":
                return new ProfitCommand(date1, date2, String.join(" ", Arrays.copyOfRange(args,6,args.length)),"TYPE");
        }
        throw new ParserException(ParserErrorMessage.INVALID_COMMAND_FORMAT);
    }
}
