package cube.logic.parser;

import cube.exception.CubeException;
import cube.logic.command.SoldCommand;

public class SoldCommandParser implements ParserPrototype<SoldCommand> {

	public SoldCommand parse(String[] args) throws CubeException {
		int foodNameIndex = -1;
		int quantityIndex = -1;
		for (int i = 1; i < args.length; i ++) {
			if (args[i].equals("-n")) {
				foodNameIndex = i;
			}
			if (args[i].equals("-q")) {
				quantityIndex = i;
			}
		}
		if(foodNameIndex == -1 || quantityIndex == -1) {
			throw new CubeException("Not enough parameters. Please enter both food name and quantity sold.");
		}
		return new SoldCommand(args[foodNameIndex+1],Integer.parseInt(args[quantityIndex+1]));
	}
}