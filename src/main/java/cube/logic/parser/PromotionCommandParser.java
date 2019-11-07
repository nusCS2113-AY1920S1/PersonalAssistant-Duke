/**
 * The command adds a new promotion to the promotion list.
 *
 * @author parvathi14
 */

package cube.logic.parser;

import cube.logic.command.PromotionCommand;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;
import cube.model.promotion.Promotion;

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

        if (foodNameIndex == -1 && discountIndex == -1 && startDateIndex == -1 && endDateIndex == -1) {
            //print list of promotions
            //return;
        }

        if (foodNameIndex == -1 || discountIndex == -1 || endDateIndex == -1) {
            throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
        }

        String foodName = ParserUtil.findFullString(args, foodNameIndex);
        Promotion tempPromotion = new Promotion(foodName);

        if (discountIndex != -1) {
            tempPromotion.setDiscount(Double.parseDouble(args[discountIndex+1]));
        }

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
