package cube.logic.parser;

import cube.logic.command.ListCommand;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;
import cube.model.FoodList.SortType;

public class ListCommandParser implements ParserPrototype<ListCommand> {

	public ListCommand parse(String[] args) throws ParserException {
		if (args.length == 1) {
			return new ListCommand();
		}
		if (args.length == 2) {
			throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
		}
		return new ListCommand(SortType.valueOf(args[2].toUpperCase()));
	}
}