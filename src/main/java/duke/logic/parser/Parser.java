package duke.logic.parser;

import java.util.Optional;

import duke.exception.DukeException;
import duke.logic.command.Command;

public interface Parser<T extends Command> {
    T parse(Optional<String> filter, String args) throws DukeException;
}
