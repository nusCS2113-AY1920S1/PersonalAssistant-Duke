package parser;

import command.Command;
import command.ViewCommand;
import exception.DukeException;

public class ViewParser extends DescriptionParser {

    public ViewParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws DukeException {
        super.extract();
        return new ViewCommand(taskFeatures);
    }

}
