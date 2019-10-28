package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgSpec;

public class ImpressionDeleteCommand extends ImpressionCommand {

    @Override
    protected ArgSpec getSpec() {
        return ImpressionDeleteSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) {
        // TODO
    }
}
