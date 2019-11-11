//@@author ZKathrynx

package cube.logic.parser;

import cube.logic.command.DeleteCommand;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;

import java.util.Arrays;

/**
 * Parse delete command.
 */
public class DeleteCommandParser implements ParserPrototype<DeleteCommand> {

	/**
	 * Parse user delete command.
	 * @param args user inputs.
	 * @return delete command with relative parameters.
	 * @throws ParserException when user input is illegal.
	 */
	public DeleteCommand parse(String[] args) throws ParserException {
		String[] params = new String[]{"-i","-n","-t","-all"};

		if (ParserUtil.hasInvalidParameters(args,params)) {
			throw new ParserException(ParserErrorMessage.INVALID_PARAMETER);
		}
		if (ParserUtil.hasRepetitiveParameters(args)) {
			throw new ParserException(ParserErrorMessage.REPETITIVE_PARAMETER);
		}
		boolean isOnlyCommand = (args.length == 1);
		boolean isNotAll = (args.length == 2 && !args[1].equals("-all"));
		if (isOnlyCommand || isNotAll) {
			throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
		}

		switch (args[1]) {
			case "-i":
				if (!ParserUtil.isValidInteger(args[2])) {
					throw new ParserException(ParserErrorMessage.INVALID_INTEGER);
				}
				return new DeleteCommand(Integer.parseInt(args[2]),"INDEX");
			case "-n":
				return new DeleteCommand(String.join(" ",
						Arrays.copyOfRange(args,2,args.length)),"NAME");
			case "-t":
				return new DeleteCommand(String.join(" ",
						Arrays.copyOfRange(args,2,args.length)),"TYPE");
			case "-all":
				return new DeleteCommand(String.join(" ",
						Arrays.copyOfRange(args,2,args.length)),"ALL");
			default:
				throw new ParserException(ParserErrorMessage.INVALID_COMMAND_FORMAT);
		}

	}
}