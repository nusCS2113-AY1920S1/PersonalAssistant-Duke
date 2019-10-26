package duke.parser;
import duke.command.Command;
import duke.exception.DukeException;
import duke.tasklist.TaskList;

import java.util.Optional;

public interface Parser<T extends Command> {
    T parse(Optional<String> filter, String args) throws DukeException;
}
