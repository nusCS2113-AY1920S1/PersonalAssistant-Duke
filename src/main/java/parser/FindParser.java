package parser;

import command.Command;
import command.FindCommand;
import exception.DukeException;

public class FindParser extends DescriptionParser {
    public FindParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws DukeException {
        super.extract();
        return new FindCommand(taskFeatures);
    }
}
