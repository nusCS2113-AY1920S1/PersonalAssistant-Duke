//@@author ZKathrynx
package cube.logic.parser;

import cube.logic.command.*;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;

// design pattern copied from address book
// reference: https://github.com/nusCS2113-AY1920S1/addressbook-level3/tree/master/src/main/java/seedu/address/logic/parser

/**
 * Parse user command.
 */
public class Parser {

	/**
	 * Parse user command.
	 *
	 * Format:
	 * add foodName -t foodType -p price -s stock -e expiryDate
	 * update foodName -t foodType -p price -s stock -e expiryDate
	 * batch -i / -o
	 * config
	 * list -sort expiry/name/stock
	 * find -i index / -n foodName / -t foodType --sort expiry/name/stock
	 * profit -t1 time1 -t2 time2 -i index / -n foodName / -t foodType / -all All
	 * revenue -i index / -n foodName / -t foodType
	 * delete -i index / -n foodName / -t foodType
	 * sold -n foodName -q quantity
	 * reminder -d daysToExpiry -s stock
	 * promotion -n foodName -% discount -s startDate -e endDate
	 * help
	 * bye / exit / quit
	 *
	 * @param fullCommand the command that user inputs.
	 * @return corresponding command.
	 */
	public static Command parse (String fullCommand) throws ParserException {
		String[] inputs = fullCommand.split(" ");
		String command = inputs[0];
		command = command.trim().toLowerCase();
		switch (command) {
			//Alphabetical order
			case "add":
				return new AddCommandParser().parse(inputs);
			case "list":
				return new ListCommandParser().parse(inputs);
			case "find":
				return new FindCommandParser().parse(inputs);
			case "update":
				return new UpdateCommandParser().parse(inputs);
			case "profit":
				return new ProfitCommandParser().parse(inputs);
			case "delete":
				return new DeleteCommandParser().parse(inputs);
			case "sold":
				return new SoldCommandParser().parse(inputs);
			case "batch":
				return new BatchCommandParser().parse(inputs);
			case "config":
				return new ConfigCommandParser().parse(inputs);
			case "help":
				return new HelpCommand();
			case "promotion":
				if (inputs[1] == "-list") {
					return new ListPromotionCommand();
				} else if (inputs[1] == "-delete") {
					return new DeletePromotionCommandParser().parse(inputs);
				} else {
					return new PromotionCommandParser().parse(inputs);
				}
			case "reminder":
				return new ReminderCommandParser().parse(inputs);
			case "revenue" :
				//TODO: improve generate revenue
				return new RevenueCommandParser().parse(inputs);
			case "bye":
			case "exit":
			case "quit":
				return new ExitCommand();
			default:
				throw new ParserException(ParserErrorMessage.INVALID_COMMAND);
		}
	}
}

