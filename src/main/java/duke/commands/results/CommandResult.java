package duke.commands.results;

import duke.model.TaskList;
import duke.model.events.Task;
import duke.model.locations.BusStop;

import java.util.ArrayList;

public abstract class CommandResult {
    protected String message;

    public String getMessage() { return message; };
}
