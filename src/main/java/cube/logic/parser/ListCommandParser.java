package cube.logic.parser;

import cube.logic.command.ListCommand;

public class ListCommandParser implements ParserPrototype<ListCommand> {

	public ListCommand parse(String[] args) {
		return new ListCommand();
	}
}