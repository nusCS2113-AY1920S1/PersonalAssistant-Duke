package duke.command;

import duke.DukeCore;
import duke.data.DukeData;
import duke.exception.DukeException;

public abstract class DataSpec extends ArgSpec {

    public void execute(DukeCore core, DataCommand cmd, DukeData data) throws DukeException {
        this.cmd = cmd;
        executeWithData(core, data);
        this.cmd = null;
    }

    protected abstract void executeWithData(DukeCore core, DukeData data) throws DukeException;
}