package cube.logic.parser;

import cube.logic.command.SoldCommand;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;

import java.util.Date;

//TODO: remove quantity parameter
public class SoldCommandParser implements ParserPrototype<SoldCommand> {

	public SoldCommand parse(String[] args) throws ParserException {
		int foodNameIndex = 1;
		int quantityIndex = -1;
		int dateIndex = -1;
		String[] params = new String[]{"-q","-t"};

		if(ParserUtil.hasInvalidParameters(args,params)){
			throw new ParserException(ParserErrorMessage.INVALID_PARAMETER);
		}
		if(ParserUtil.hasRepetitiveParameters(args)){
			throw new ParserException(ParserErrorMessage.REPETITIVE_PARAMETER);
		}
		for (int i = 1; i < args.length; i ++) {
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
		if(quantityIndex == -1) {
			throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
		}
		if(!ParserUtil.isValidNumber(args[quantityIndex + 1])){
			throw new ParserException(ParserErrorMessage.INVALID_NUMBER);
		}
		if(dateIndex == -1) {
			return new SoldCommand(foodName,Integer.parseInt(args[quantityIndex+1]), new Date());
		}
		return new SoldCommand(foodName,Integer.parseInt(args[quantityIndex+1]), ParserUtil.parseStringToDate(args[dateIndex+1]));
	}
}