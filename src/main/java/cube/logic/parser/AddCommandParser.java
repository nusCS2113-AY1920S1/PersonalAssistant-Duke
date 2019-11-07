//@@author ZKathrynx
package cube.logic.parser;

import cube.logic.command.AddCommand;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;
import cube.model.food.Food;

/**
 * Parse add food command.
 */
public class AddCommandParser implements ParserPrototype<AddCommand> {

	public AddCommand parse(String[] args) throws ParserException {
		int foodNameIndex = 1;
		int foodTypeIndex = -1;
		int priceIndex = -1;
		int stockIndex = -1;
		int expiryDateIndex = -1;
		String[] params = new String[]{"-t","-p","-s","-e"};

		if(ParserUtil.hasInvalidParameters(args,params)){
			throw new ParserException(ParserErrorMessage.INVALID_PARAMETER);
		}
		if(ParserUtil.hasRepetitiveParameters(args)){
			throw new ParserException(ParserErrorMessage.REPETITIVE_PARAMETER);
		}

		for (int i = 1; i < args.length; i ++) {
			if (args[i].equals("-t")) {
				foodTypeIndex = i;
			}
			if (args[i].equals("-p")) {
				priceIndex = i;
			}
			if (args[i].equals("-s")) {
				stockIndex = i;
			}
			if (args[i].equals("-e")) {
				expiryDateIndex = i;
			}
		}
		String foodName = ParserUtil.findFullString(args,foodNameIndex);
		if (foodName.equals("")) {
			throw new ParserException(ParserErrorMessage.INVALID_NAME);
		}
		Food tempFood = new Food(foodName);
		if (foodTypeIndex != -1) {
			if (!ParserUtil.hasField(args,foodTypeIndex+1)) {
				throw new ParserException(ParserErrorMessage.EMPTY_FIELD);
			}
			tempFood.setType(ParserUtil.findFullString(args,foodTypeIndex+1));
		}
		if (priceIndex != -1) {
			if (!ParserUtil.hasField(args,priceIndex+1)) {
				throw new ParserException(ParserErrorMessage.EMPTY_FIELD);
			}
            if(!ParserUtil.isValidNumber(args[priceIndex+1])){
                throw new ParserException(ParserErrorMessage.INVALID_NUMBER);
            }
			tempFood.setPrice(Integer.parseInt(args[priceIndex+1]));
		}
		if (stockIndex != -1) {
			if (!ParserUtil.hasField(args,stockIndex+1)) {
				throw new ParserException(ParserErrorMessage.EMPTY_FIELD);
			}
		    if(!ParserUtil.isValidNumber(args[stockIndex+1])){
		        throw new ParserException(ParserErrorMessage.INVALID_NUMBER);
            }
			tempFood.setStock(Integer.parseInt(args[stockIndex+1]));
		}
		if (expiryDateIndex != -1) {
			if (!ParserUtil.hasField(args,expiryDateIndex+1)) {
				throw new ParserException(ParserErrorMessage.EMPTY_FIELD);
			}
			tempFood.setExpiryDate(ParserUtil.parseStringToDate(args[expiryDateIndex+1]));
		}
		return new AddCommand(tempFood);
	}

}