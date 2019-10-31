package cube.logic.parser;

import cube.logic.command.PromotionCommand;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;
import cube.model.food.Food;
import cube.model.promotion.Promotion;

import java.util.Date;

public class PromotionCommandParser implements ParserPrototype<PromotionCommand> {
    public PromotionCommand parse(String[] args) throws ParserException {
        int foodNameIndex = 1;
        int startDateIndex = -1;
        int endDateIndex = -1;
        String[] params = new String[]{"-s","-e"};

        if(ParserUtil.hasInvalidParameters(args,params)){
            throw new ParserException(ParserErrorMessage.INVALID_PARAMETER);
        }
        if(ParserUtil.hasRepetitiveParameters(args)){
            throw new ParserException(ParserErrorMessage.REPETITIVE_PARAMETER);
        }

        for (int i = 1; i < args.length; i++) {
            if(args[i].equals("-s")) {
                startDateIndex = i;
            }

            if(args[i].equals("-e")) {
                endDateIndex = i;
            }
        }

        String foodName = ParserUtil.findFullString(args,foodNameIndex);
        if (foodName.equals("")) {
            throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
        }
        Promotion tempPromotion = new Promotion(foodName);

        //todo: parse discount

        if (startDateIndex != -1) {
            tempPromotion.setStartDate(ParserUtil.parseStringToDate(args[startDateIndex+1]));
        } else {
            tempPromotion.setStartDate(new Date());
        }

        if (endDateIndex != -1) {
            tempPromotion.setEndDate(ParserUtil.parseStringToDate(args[endDateIndex+1]));
        }

        return new PromotionCommand(tempPromotion);
    }
}
