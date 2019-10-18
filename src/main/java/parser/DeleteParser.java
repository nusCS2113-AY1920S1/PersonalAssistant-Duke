package parser;

import command.Command;
import command.DeleteCommand;
import exception.DukeException;

public class DeleteParser extends IndexParser {

    public DeleteParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws DukeException {
        super.extract();
        return new DeleteCommand(indexOfTask);
    }

}
