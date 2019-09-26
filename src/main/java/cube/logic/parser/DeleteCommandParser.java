package cube.logic.parser;

import cube.logic.command.DeleteCommand;

public class DeleteCommandParser implements ParserPrototype<DeleteCommand> {

	public DeleteCommand parse(String args) {
		return new DeleteCommand(0);
	}
}