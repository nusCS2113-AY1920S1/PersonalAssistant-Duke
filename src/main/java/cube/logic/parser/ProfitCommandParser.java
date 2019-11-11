//@@author LL-Pengfei
/**
 * ProfitCommandParser.java
 * Parse the command for ProfitCommand.
 */

package cube.logic.parser;

import cube.exception.CubeException;
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
        String[] params = new String[]{"-i","-t","-n","-all"};
        if (args.length < 6) {
            //generating profits and revenue requires at least 6 arguments, which is the case of generating for all
            //food within the given time period
            throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
        }
        if (!args[5].equals("-all") && args.length < 7) {
            //if generating profits and revenue is not for all food within the period, but only for a specific type
            //of food, then it requires at least 7 arguments in total.
            throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
        }

        assert args[1].equals("-t1") : "The second parameter is not '-t1'.";
        assert args[3].equals("-t2") : "The fourth parameter is not '-t2'.";

        Date dateI = ParserUtil.parseStringToDate(args[2]); //the start date of the period (initial)
        Date dateF = ParserUtil.parseStringToDate(args[4]); //the end date of the period (final)
        assert dateI.compareTo(dateF) <= 0 : "The entered start date is after the entered end date.";

        switch (args[5]) {
            case "-all":
                return new ProfitCommand(dateI, dateF, "ALL");
            case "-i":
                return new ProfitCommand(dateI, dateF, Integer.parseInt(args[6]),"INDEX");
            case "-n":
                return new ProfitCommand(dateI, dateF, String.join(" ",
                        Arrays.copyOfRange(args,6,args.length)),"NAME");
            case "-t":
                return new ProfitCommand(dateI, dateF, String.join(" ",
                        Arrays.copyOfRange(args,6,args.length)),"TYPE");
            default:
                throw new ParserException(ParserErrorMessage.INVALID_COMMAND_FORMAT);
        }
    }
}
