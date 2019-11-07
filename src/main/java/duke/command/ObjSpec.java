package duke.command;

import duke.DukeCore;
import duke.data.DukeObject;
import duke.exception.DukeException;

public abstract class ObjSpec extends ArgSpec {

    public void execute(DukeCore core, ObjCommand cmd, DukeObject obj) throws DukeException {
        this.cmd = cmd;
        executeWithObj(core, obj);
        this.cmd = null;
    }

    protected abstract void executeWithObj(DukeCore core, DukeObject obj) throws DukeException;
}