package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

public abstract class CommandSpec {

    protected Command cmd;

    public void execute(DukeCore core, Command cmd) throws DukeException {
        this.cmd = cmd;
        execute(core);
        this.cmd = null;
    }

    protected abstract void execute(DukeCore core) throws DukeException;

    public String getHelp() {
        return "https://github.com/AY1920S1-CS2113-T14-1/main/blob/master/docs/UserGuide.adoc";
    }
}
