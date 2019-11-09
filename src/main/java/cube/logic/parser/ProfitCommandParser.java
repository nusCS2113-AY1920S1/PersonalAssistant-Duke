//@@author LL-Pengfei
/**
 * ProfitCommandParser.java
 * Parse the command for ProfitCommand.
 */
package cube.logic.parser;

import cube.logic.command.ProfitCommand;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;

import java.util.Arrays;
import java.util.Date;

/**
 * This class parses the command for ProfitCommand, i.e. the command to generate profits and revenue.
 */
public class ProfitCommandParser implements ParserPrototype<ProfitCommand> {
    /**
     * Parses the command for ProfitCommand.
     *
     * @param args The tokenized arguments.
     * @return Call the ProfitCommand constructor with arguments corresponding to
     *         the command type of generating profits and revenue.
     * @throws ParserException if Parsing is unsuccessful.
     */
    public ProfitCommand parse(String[] args) throws ParserException {
        if (args.length < 6) {
            throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
        }
        if (!args[5].equals("-all") && args.length < 7) {
            //if generating profits and revenue is not for all food within the period, but only for a specific type
            //of food, then it requires at least 7 arguments in total.
            throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
        }
        Date date_i = ParserUtil.parseStringToDate(args[2]); //the start date of the period (initial)
        Date date_f = ParserUtil.parseStringToDate(args[4]); //the end date of the period (final)
        switch (args[5]) {
            case "-all":
                return new ProfitCommand(date_i, date_f, "ALL");
            case "-i":
                return new ProfitCommand(date_i, date_f, Integer.parseInt(args[6]),"INDEX");
            case "-n":
                return new ProfitCommand(date_i, date_f, String.join(" ", Arrays.copyOfRange(args,6,args.length)),"NAME");
            case "-t":
                return new ProfitCommand(date_i, date_f, String.join(" ", Arrays.copyOfRange(args,6,args.length)),"TYPE");
        }
        throw new ParserException(ParserErrorMessage.INVALID_COMMAND_FORMAT);
    }
}
