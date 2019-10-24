package cube.logic.parser;

import cube.logic.command.AddCommand;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;
import cube.model.Food;

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
			throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
		}
		Food tempFood = new Food(foodName);
		if (foodTypeIndex != -1) {
			tempFood.setType(ParserUtil.findFullString(args,foodTypeIndex+1));
		}
		if (priceIndex != -1) {
			tempFood.setPrice(Integer.parseInt(args[priceIndex+1]));
		}
		if (stockIndex != -1) {
			tempFood.setStock(Integer.parseInt(args[stockIndex+1]));
		}
		if (expiryDateIndex != -1) {
			tempFood.setExpiryDate(ParserUtil.parseStringToDate(args[expiryDateIndex+1]));
		}
		return new AddCommand(tempFood);
	}

}