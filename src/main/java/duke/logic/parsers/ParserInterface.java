package duke.logic.parsers;

import duke.logic.commands.Command;
import duke.commons.exceptions.DukeException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public interface ParserInterface<T extends Command> {

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    T parse(String userInput) throws DukeException;
}

