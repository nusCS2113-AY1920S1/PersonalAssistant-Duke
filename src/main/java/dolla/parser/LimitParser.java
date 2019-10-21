package dolla.parser;

import dolla.command.*;

/**
 * This class handles all limit related parsing.
 */
public class LimitParser extends Parser {

    public LimitParser(String inputLine) {
        super(inputLine);
    }

    @Override
    public Command handleInput(String mode, String inputLine) {
        if(commandToRun.equals("limits")) {
          return new ShowListCommand(mode);
        } else if (commandToRun.equals("set")) {
            //add limit command
        } else if (commandToRun.equals("remove")) {
            return new RemoveCommand(mode, inputArray[1]);
        } else if (commandToRun.equals("edit")) {
            //edit limit command
        } else if (commandToRun.equals("search")) {
            String component = inputArray[1];
            String content = inputArray[2];
            return new SearchCommand(mode, component, content);
        }
        return null;
    }
}
