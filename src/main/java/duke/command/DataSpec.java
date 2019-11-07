package duke.command;

import duke.DukeCore;
import duke.data.DukeData;
import duke.exception.DukeException;

public abstract class DataSpec extends ArgSpec {

    protected DukeData data;

    public void execute(DukeCore core, DataCommand cmd, DukeData data) throws DukeException {
        this.data = data;
        super.execute(core, cmd);
        this.data = null;
    }
}