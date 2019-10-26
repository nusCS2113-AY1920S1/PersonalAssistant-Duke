package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

public class UpCommand extends ArgCommand {
    @Override
    protected ArgSpec getSpec() {
        return UpSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        core.uiContext.moveUpOneContext();
    }
}
