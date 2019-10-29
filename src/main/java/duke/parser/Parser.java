package duke.parser;

import java.util.Optional;

import duke.command.Command;
import duke.exception.DukeException;

public interface Parser<T extends Command> {
    T parse(Optional<String> filter, String args) throws DukeException;
}
