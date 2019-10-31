//@@author ZKathrynx
package cube.logic.parser;

import cube.logic.command.ListCommand;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;
import cube.model.food.FoodList.SortType;

public class ListCommandParser implements ParserPrototype<ListCommand> {

	public ListCommand parse(String[] args) throws ParserException {
		String[] params = new String[]{"-sort"};

		if(ParserUtil.hasInvalidParameters(args,params)){
			throw new ParserException(ParserErrorMessage.INVALID_PARAMETER);
		}
		if(ParserUtil.hasRepetitiveParameters(args)){
			throw new ParserException(ParserErrorMessage.REPETITIVE_PARAMETER);
		}

		if (args.length == 1) {
			return new ListCommand();
		}
		if (args.length == 2) {
			throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
		}
		return new ListCommand(SortType.valueOf(args[2].toUpperCase()));
	}
}