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

	private Food tempFood = new Food();

    /**
     * Parse user add command.
     *
     * @param args user inputs.
     * @return add command with the food to be added
     * @throws ParserException when user input is illegal.
     */
    public AddCommand parse(String[] args) throws ParserException {
        final int foodNameIndex = 1;
        int foodTypeIndex = -1;
        int priceIndex = -1;
        int costIndex = -1;
        int stockIndex = -1;
        int expiryDateIndex = -1;
        String[] params = new String[] {"-t", "-p", "-c", "-s", "-e"};

        if (args.length == 1) {
            throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
        }
        if (ParserUtil.hasInvalidParameters(args, params)) {
            throw new ParserException(ParserErrorMessage.INVALID_PARAMETER);
        }
        if (ParserUtil.hasRepetitiveParameters(args)) {
            throw new ParserException(ParserErrorMessage.REPETITIVE_PARAMETER);
        }

        for (int i = 1; i < args.length; i++) {
            if (args[i].equals("-t")) {
                foodTypeIndex = i;
            }
            if (args[i].equals("-p")) {
                priceIndex = i;
            }
			if (args[i].equals("-c")) {
				costIndex = i;
			}
            if (args[i].equals("-s")) {
                stockIndex = i;
            }
            if (args[i].equals("-e")) {
                expiryDateIndex = i;
            }
        }

        String foodName = ParserUtil.findFullString(args, foodNameIndex);
        if (foodName.equals("")) {
            throw new ParserException(ParserErrorMessage.INVALID_NAME);
        }
        tempFood.setName(foodName);
        if (foodTypeIndex != -1) {
            if (!ParserUtil.hasField(args, foodTypeIndex + 1)) {
                throw new ParserException(ParserErrorMessage.EMPTY_FIELD);
            }
            tempFood.setType(ParserUtil.findFullString(args, foodTypeIndex + 1));
        }
        if (priceIndex != -1) {
            if (!ParserUtil.hasField(args, priceIndex + 1)) {
                throw new ParserException(ParserErrorMessage.EMPTY_FIELD);
            }
            if (!ParserUtil.isValidNumber(args[priceIndex + 1])) {
                throw new ParserException(ParserErrorMessage.INVALID_INTEGER);
            }
            tempFood.setPrice(Double.parseDouble(args[priceIndex + 1]));
        }
		if (costIndex != -1) {
			if (!ParserUtil.hasField(args, costIndex + 1)) {
				throw new ParserException(ParserErrorMessage.EMPTY_FIELD);
			}
			if (!ParserUtil.isValidNumber(args[costIndex + 1])) {
				throw new ParserException(ParserErrorMessage.INVALID_NUMBER);
			}
			tempFood.setCost(Double.parseDouble(args[costIndex + 1]));
		}
        if (stockIndex != -1) {
            if (!ParserUtil.hasField(args, stockIndex + 1)) {
                throw new ParserException(ParserErrorMessage.EMPTY_FIELD);
            }
            if (!ParserUtil.isValidInteger(args[stockIndex + 1])) {
                throw new ParserException(ParserErrorMessage.INVALID_NUMBER);
            }
            tempFood.setStock(Integer.parseInt(args[stockIndex + 1]));
        }
        if (expiryDateIndex != -1) {
            if (!ParserUtil.hasField(args, expiryDateIndex + 1)) {
                throw new ParserException(ParserErrorMessage.EMPTY_FIELD);
            }
            tempFood.setExpiryDate(ParserUtil.parseStringToDate(args[expiryDateIndex + 1]));
        }
        return new AddCommand(tempFood);
    }

    /**
     * Getter for temp food.
     *
     * @return temp food.
     */
    public Food getTempFood() {
        return tempFood;
    }
}