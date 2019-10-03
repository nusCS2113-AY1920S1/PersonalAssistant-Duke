package cube.logic.parser;

import cube.logic.command.SoldCommand;

public class SoldCommandParser implements ParserPrototype<SoldCommand> {

	public SoldCommand parse(String args) {
		return new SoldCommand("fuckcs2113t", 0);
	}
}