package cube.logic.parser;

import cube.logic.command.SoldCommand;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;

import java.util.Date;

/**
 * Parse sold command.
 */
public class SoldCommandParser implements ParserPrototype<SoldCommand> {

	/**
	 * Parse user sold command.
	 * @param args user inputs.
	 * @return sold command with relative parameters.
	 * @throws ParserException when user input is illegal.
	 */
	public SoldCommand parse(String[] args) throws ParserException {
		final int foodNameIndex = 1;
		int quantityIndex = -1;
		int dateIndex = -1;
		String[] params = new String[]{"-q","-t"};

		if (args.length == 1) {
			throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
		}
		if (ParserUtil.hasInvalidParameters(args,params)) {
			throw new ParserException(ParserErrorMessage.INVALID_PARAMETER);
		}
		if (ParserUtil.hasRepetitiveParameters(args)) {
			throw new ParserException(ParserErrorMessage.REPETITIVE_PARAMETER);
		}
		for (int i = 1; i < args.length; i++) {
			if (args[i].equals("-q")) {
				quantityIndex = i;
			}
			if (args[i].equals("-t")) {
				dateIndex = i;
			}
		}

		String foodName = ParserUtil.findFullString(args,foodNameIndex);
		if (foodName.equals("")) {
			throw new ParserException(ParserErrorMessage.INVALID_NAME);
		}
		if (quantityIndex == -1) {
			throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
		}
		if (!ParserUtil.hasField(args,quantityIndex + 1)) {
			throw new ParserException(ParserErrorMessage.EMPTY_FIELD);
		}
		if (!ParserUtil.isValidInteger(args[quantityIndex + 1])) {
			throw new ParserException(ParserErrorMessage.INVALID_INTEGER);
		}
		if (dateIndex == -1) {
			return new SoldCommand(foodName,Integer.parseInt(args[quantityIndex + 1]), new Date());
		}
		if (!ParserUtil.hasField(args,dateIndex + 1)) {
			throw new ParserException(ParserErrorMessage.EMPTY_FIELD);
		}
		return new SoldCommand(foodName,Integer.parseInt(args[quantityIndex + 1]),
				ParserUtil.parseStringToDate(args[dateIndex + 1]));
	}
}