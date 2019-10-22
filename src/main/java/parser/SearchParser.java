package parser;

import command.Command;
import command.SearchCommand;
import exception.DukeException;

public class SearchParser extends IndexParser {

    public SearchParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws DukeException {
        super.extract();
        Long duration;
        duration = (long) indexOfTask;
        return new SearchCommand(duration);
    }
}
