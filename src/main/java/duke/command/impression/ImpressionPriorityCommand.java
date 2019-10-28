package duke.command.impression;

import duke.command.ArgSpec;

public class ImpressionPriorityCommand extends ImpressionCommand {

    @Override
    protected ArgSpec getSpec() {
        return ImpressionPrioritySpec.getSpec();
    }
}
