package duke.command;

import duke.DukeCore;
import duke.data.DukeObject;
import duke.data.SearchResults;
import duke.exception.DukeException;

public abstract class ObjSpec extends ArgSpec {

    protected ObjCommand cmd;

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