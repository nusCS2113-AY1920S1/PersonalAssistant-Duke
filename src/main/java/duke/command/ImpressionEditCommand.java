package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

public class ImpressionEditCommand extends DukeDataCommand {

    @Override
    protected ArgSpec getSpec() {
        return ImpressionEditSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        //todo
    }

}
