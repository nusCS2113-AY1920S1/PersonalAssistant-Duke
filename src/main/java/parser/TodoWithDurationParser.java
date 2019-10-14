package parser;

import command.AddCommand;
import command.Command;
import exception.DukeException;

public class TodoWithDurationParser extends TodoParser {

    public TodoWithDurationParser(String userInput) {
        super(userInput);
        this.checkType = Flag.FOR.getFlag();
    }

    @Override
    public Command parse() throws DukeException {
        super.extract();

        String substring = userInput.split(checkType, 2)[1].trim();
        int duration;
        try {
            duration = Integer.parseInt(substring.split("\\s+", 2)[0].trim());
        } catch (NumberFormatException e) {
            throw new DukeException("Invalid duration format. Duration must be a number");
        }
        return new AddCommand(command, taskDescription, duration);
    }
}
