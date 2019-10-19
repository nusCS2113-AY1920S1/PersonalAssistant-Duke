package cube.logic.parser;

import cube.exception.CubeException;
import cube.logic.command.DeleteCommand;

import java.util.Arrays;

public class DeleteCommandParser implements ParserPrototype<DeleteCommand> {

	public DeleteCommand parse(String[] args) throws CubeException {
		if (args.length < 3) {
			throw new CubeException("Not enough parameters. Please enter index you want to delete.");
		}

		switch (args[1]) {
			case "-i":
				return new DeleteCommand(Integer.parseInt(args[2]),"INDEX");
			case "-n":
				return new DeleteCommand(String.join(" ", Arrays.copyOfRange(args,2,args.length)),"NAME");
			case "-t":
				return new DeleteCommand(String.join(" ", Arrays.copyOfRange(args,2,args.length)),"TYPE");
		}
		return null;
	}
}