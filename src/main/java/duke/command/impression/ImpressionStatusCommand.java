package duke.command.impression;

import duke.command.ArgCommand;
import duke.command.ArgSpec;

public class ImpressionStatusCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return ImpressionStatusSpec.getSpec();
    }
}
