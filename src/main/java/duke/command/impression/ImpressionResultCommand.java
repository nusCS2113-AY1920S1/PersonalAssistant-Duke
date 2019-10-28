package duke.command.impression;

import duke.command.ArgSpec;

public class ImpressionResultCommand extends ImpressionCommand {

    @Override
    protected ArgSpec getSpec() {
        return ImpressionResultSpec.getSpec();
    }

}
