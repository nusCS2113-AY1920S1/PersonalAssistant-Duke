package duke.command;

import duke.DukeCore;
import duke.exception.DukeFatalException;

public class ByeSpec extends CommandSpec {
    private static final ByeSpec spec = new ByeSpec();

    public static ByeSpec getSpec() {
        return spec;
    }

    @Override
    protected void execute(DukeCore core) throws DukeFatalException {
        core.writeJsonFile();
        core.stop();
    }
}
