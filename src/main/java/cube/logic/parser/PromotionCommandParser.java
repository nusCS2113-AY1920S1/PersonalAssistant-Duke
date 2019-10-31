package cube.logic.parser;

import cube.logic.command.PromotionCommand;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;

import java.util.Date;

public class PromotionCommandParser implements ParserPrototype<PromotionCommand> {
    public PromotionCommand parse(String[] args) throws ParserException {
        int foodNameIndex = -1;
        int discountIndex = -1;
        int startDateIndex = -1;
        int endDateIndex = -1;

        for (int i = 1; i < args.length; i++) {

            if(args[i].equals("-n")) {
                foodNameIndex = i;
            }

            if(args[i].equals("-%")) {
                discountIndex = i;
            }

            if(args[i].equals("-s")) {
                startDateIndex = i;
            }

            if(args[i].equals("-e")) {
                endDateIndex = i;
            }
        }

        if (discountIndex == -1 || endDateIndex == -1) {
            throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
        }

        if (startDateIndex == -1) {
            return new PromotionCommand(args[foodNameIndex+1], Double.parseDouble(args[discountIndex+1]), new Date(), ParserUtil.parseStringToDate(args[endDateIndex+1]));
        }

        return new PromotionCommand(args[foodNameIndex+1], Double.parseDouble(args[discountIndex+1]), ParserUtil.parseStringToDate(args[startDateIndex+1]), ParserUtil.parseStringToDate(args[endDateIndex+1]));
    }
}
