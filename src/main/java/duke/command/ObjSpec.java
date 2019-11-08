package duke.command;

import duke.DukeCore;
import duke.data.DukeObject;
import duke.data.SearchResults;
import duke.exception.DukeException;

public abstract class ObjSpec extends ArgSpec {

    protected ObjCommand cmd;

    @Override
    public void executeWithCmd(DukeCore core, ArgCommand cmd) throws DukeException {
        assert (cmd instanceof ObjCommand); // TODO find an elegant way to enforce this
        this.cmd = (ObjCommand) cmd;
        execute(core);
        this.cmd = null;
    }

    /**
     * todo write documention.
     * @param core program core to execute command
     * @param cmd the command used to execute with object
     * @param obj the DukeObject context the command is executed within.
     * @throws DukeException if the command cannot be executed
     */
    public void execute(DukeCore core, ObjCommand cmd, DukeObject obj) throws DukeException {
        this.cmd = cmd;
        executeWithObj(core, obj);
        this.cmd = null;
    }

    protected void processResults(DukeCore core, SearchResults results) throws DukeException {
        if (results.getCount() == 0) {
            throw new DukeException("No results found for '" + results.getName() + "'!");
        } else if (results.getCount() == 1) {
            executeWithObj(core, results.getResult(0));
        } else {
            core.search(results, cmd);
        }
    }

    protected abstract void executeWithObj(DukeCore core, DukeObject obj) throws DukeException;
}