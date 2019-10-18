package parser;

import command.Command;
import command.DoneCommand;
import exception.DukeException;

public class DoneParser extends IndexParser {

    public DoneParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws DukeException {
        super.extract();
        return new DoneCommand(indexOfTask);
    }
}
