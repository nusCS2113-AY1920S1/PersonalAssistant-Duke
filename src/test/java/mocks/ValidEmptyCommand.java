package mocks;

import duke.command.ArgCommand;
import duke.command.ArgSpec;

public class ValidEmptyCommand extends ArgCommand {
    @Override
    protected ArgSpec getSpec() {
        return ValidEmptySpec.getSpec();
    }
}
