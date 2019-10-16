package duke.parsers;

import duke.commands.Command;
import duke.exceptions.DukeException;

public interface ParserInterface<T extends Command> {

    T parse(String userInput) throws DukeException;
}

