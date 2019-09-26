package cube.logic;

import cube.logic.command.*;
import cube.logic.parser.*;

// design pattern copied from address book
// reference: https://github.com/nusCS2113-AY1920S1/addressbook-level3/tree/master/src/main/java/seedu/address/logic/parser

public class Parser {
	// for testing

	public static Command parse(String fullCommand) {
		// test only
		switch (fullCommand) {
			case "add":
				return new AddCommandParser().parse(fullCommand);
			case "list":
				return new ListCommandParser().parse(fullCommand);
			case "delete":
				return new DeleteCommandParser().parse(fullCommand);
			case "sold":
				return new SoldCommandParser().parse(fullCommand);
			case "help":
				return new HelpCommand();
			case "bye":
			case "exit":
			case "quit":
				return new ExitCommand();
			default:
				return new ExitCommand();
		}
	}
}

