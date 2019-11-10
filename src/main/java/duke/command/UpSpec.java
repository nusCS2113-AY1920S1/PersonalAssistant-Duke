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
        core.ui.showMessage(core.uiContext.moveUpOneContext());
    }
}
