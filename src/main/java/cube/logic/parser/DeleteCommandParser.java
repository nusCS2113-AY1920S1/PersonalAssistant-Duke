package cube.logic.parser;

import cube.exception.CubeException;
import cube.logic.command.DeleteCommand;

public class DeleteCommandParser implements ParserPrototype<DeleteCommand> {

	public DeleteCommand parse(String[] args) throws CubeException {
		if (args.length < 3) {
			throw new CubeException("Not enough parameters. Please enter index you want to delete.");
		}
		return new DeleteCommand(Integer.parseInt(args[2]));
	}
}