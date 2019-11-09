package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

public class UpSpec extends CommandSpec {
    private static final UpSpec spec = new UpSpec();

    public static UpSpec getSpec() {
        return spec;
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        core.uiContext.moveUpOneContext();
        core.ui.showMessage("You are now at the " + core.uiContext.getContext().toString() + " context.");
    }
}
