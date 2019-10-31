package cube.logic.parser;

import cube.logic.command.SoldCommand;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;

import java.util.Date;

public class SoldCommandParser implements ParserPrototype<SoldCommand> {

	public SoldCommand parse(String[] args) throws ParserException {
		int foodNameIndex = -1;
		int quantityIndex = -1;
		int dateIndex = -1;
		String[] params = new String[]{"-n","-q","-t"};

		if(ParserUtil.hasInvalidParameters(args,params)){
			throw new ParserException(ParserErrorMessage.INVALID_PARAMETER);
		}
		if(ParserUtil.hasRepetitiveParameters(args)){
			throw new ParserException(ParserErrorMessage.REPETITIVE_PARAMETER);
		}
		for (int i = 1; i < args.length; i ++) {
			if (args[i].equals("-n")) {
				foodNameIndex = i;
			}
			if (args[i].equals("-q")) {
				quantityIndex = i;
			}
			if (args[i].equals("-t")) {
				dateIndex = i;
			}
		}
		if(foodNameIndex == -1 || quantityIndex == -1) {
			throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
		}

		if(dateIndex == -1) {
			if(!ParserUtil.isValidNumber(args[quantityIndex + 1])){
				throw new ParserException(ParserErrorMessage.INVALID_NUMBER);
			}
			return new SoldCommand(args[foodNameIndex+1],Integer.parseInt(args[quantityIndex+1]), new Date());
		}
		if(!ParserUtil.isValidNumber(args[dateIndex + 1])){
			throw new ParserException(ParserErrorMessage.INVALID_NUMBER);
		}
		if(!ParserUtil.isValidNumber(args[quantityIndex + 1])){
			throw new ParserException(ParserErrorMessage.INVALID_NUMBER);
		}
		return new SoldCommand(args[foodNameIndex+1],Integer.parseInt(args[quantityIndex+1]), ParserUtil.parseStringToDate(args[dateIndex+1]));
	}
}