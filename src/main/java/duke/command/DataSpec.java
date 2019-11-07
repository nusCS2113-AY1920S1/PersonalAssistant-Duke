package duke.command;

import duke.DukeCore;
import duke.data.DukeData;
import duke.exception.DukeException;

public abstract class DataSpec extends ArgSpec {

    private DukeData data;

    public void execute(DukeCore core, ArgCommand cmd, DukeData data) throws DukeException {
        this.data = data;
        execute(core, cmd);
        this.data = null;
    }
}
