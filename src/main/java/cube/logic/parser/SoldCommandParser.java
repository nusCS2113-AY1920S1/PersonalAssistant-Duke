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
			return new SoldCommand(args[foodNameIndex+1],Integer.parseInt(args[quantityIndex+1]), new Date());
		}
		return new SoldCommand(args[foodNameIndex+1],Integer.parseInt(args[quantityIndex+1]), ParserUtil.parseStringToDate(args[dateIndex+1]));
	}
}