package duke.command.impression;

import duke.command.ArgSpec;

public class ImpressionStatusCommand extends ImpressionCommand {

    @Override
    protected ArgSpec getSpec() {
        return ImpressionStatusSpec.getSpec();
    }
}
