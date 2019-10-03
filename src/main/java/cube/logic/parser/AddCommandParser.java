package cube.logic.parser;

import cube.logic.command.AddCommand;

public class AddCommandParser implements ParserPrototype<AddCommand> {

	public AddCommand parse(String args) {
		return new AddCommand(null);
	}
}