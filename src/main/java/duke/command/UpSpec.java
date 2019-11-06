package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

public class UpSpec extends CommandSpec {
    private static final UpSpec spec = new UpSpec();

    public static UpSpec getSpec() {
        return spec;
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        core.uiContext.moveUpOneContext();
    }
}
