package duke.command.impression;

import duke.command.ArgCommand;
import duke.command.ArgSpec;

public class ImpressionPriorityCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return ImpressionPrioritySpec.getSpec();
    }
}
