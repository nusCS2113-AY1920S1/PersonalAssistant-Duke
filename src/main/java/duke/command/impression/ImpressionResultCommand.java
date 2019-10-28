package duke.command.impression;

import duke.command.ArgCommand;
import duke.command.ArgSpec;

public class ImpressionResultCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return ImpressionResultSpec.getSpec();
    }

}
