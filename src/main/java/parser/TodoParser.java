package parser;

import command.AddCommand;
import command.Command;
import exception.DukeException;

public class TodoParser extends DescriptionParser {

    String checkType;
    String taskDescription;

    public TodoParser(String userInput, String command) {
        super(userInput, command);
    }

    public Command parse() throws DukeException {
        super.extract();

        return new AddCommand(command, taskDescription, null, null);
    }
}
