package dolla.parser;

import dolla.command.Command;

/**
 * This class handles all limit related parsing
 */
public class LimitParser extends Parser {

    public LimitParser(String inputLine) {
        super(inputLine);
    }

    @Override
    public Command handleInput(String mode, String inputLine) {
        if (commandToRun.equals("set")) {
            //add limit command
        } else if (commandToRun.equals("remove")) {
            //remove limit command
        } else if (commandToRun.equals("edit")) {
            //edit limit command
        }
        return null;
    }
}
