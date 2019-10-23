package parser;

import command.Command;
import command.IgnoreCommand;
import exception.DukeException;

/**
 * Extract the components required for the ignore command from the user input.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class IgnoreParser extends IndexParser {

    private Boolean isIgnore;

    public IgnoreParser(String userInput, String command, Boolean isIgnore) {
        super(userInput, command);
        this.isIgnore = isIgnore;
    }

    @Override
    public Command parse() throws DukeException {
        super.extract();
        return new IgnoreCommand(indexOfTask, isIgnore);
    }

}
