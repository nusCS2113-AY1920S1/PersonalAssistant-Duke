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
     * Stores the command with the relevant switches and executes the required behaviour on the specified DukeObject.
     *
     * @param core Program core to execute command.
     * @param cmd The command holding the argument and switch values for the execution.
     * @param obj The DukeObject context the command is acting on.
     * @throws DukeException If the command cannot be executed,
     */
    public void execute(DukeCore core, ObjCommand cmd, DukeObject obj) throws DukeException {
        this.cmd = cmd;
        executeWithObj(core, obj);
        this.cmd = null;
    }

    /**
     * Throws an exception if the search failed to return any results, executes the command if the search returned only
     * one object (and hence is unambiguous), or opens a search context to allow the user to select the object
     * otherwise.
     */
    protected void processResults(DukeCore core, SearchResults results) throws DukeException {
        if (results.getCount() == 0) {
            throw new DukeException("No results found for '" + results.getName() + "'!");
        } else if (results.getCount() == 1) {
            executeWithObj(core, results.getResult(0));
        } else {
            core.search(results, cmd);
        }
    }

    @Override
    protected ArgCommand getCmd() {
        return cmd;
    }

    protected abstract void executeWithObj(DukeCore core, DukeObject obj) throws DukeException;
}