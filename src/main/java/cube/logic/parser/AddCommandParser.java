package cube.logic.parser;

import cube.exception.CubeException;
import cube.logic.command.AddCommand;
import cube.model.food.Food;

/**
 * Parse add food command.
 */
public class AddCommandParser implements ParserPrototype<AddCommand> {

	public AddCommand parse(String[] args) throws CubeException {
		int foodNameIndex = -1;
		int foodTypeIndex = -1;
		int priceIndex = -1;
		int stockIndex = -1;
		int expiryDateIndex = -1;
		for (int i = 1; i < args.length; i ++) {
			if (args[i].equals("-n")) {
				foodNameIndex = i;
			}
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
		if (foodNameIndex == -1) {
			throw new CubeException("Not enough parameters. Please enter food name.");
		}
		Food tempFood = new Food(new ParserUtil().findFullString(args,foodNameIndex));
		if (foodTypeIndex != -1) {
			tempFood.setType(new ParserUtil().findFullString(args,foodTypeIndex));
		}
		if (priceIndex != -1) {
			tempFood.setPrice(Integer.parseInt(args[priceIndex+1]));
		}
		if (stockIndex != -1) {
			tempFood.setStock(Integer.parseInt(args[stockIndex+1]));
		}
		if (expiryDateIndex != -1) {
			tempFood.setExpiryDate(new ParserUtil().parseStringToDate(args[expiryDateIndex+1]));
		}
		return new AddCommand(tempFood);
	}

}