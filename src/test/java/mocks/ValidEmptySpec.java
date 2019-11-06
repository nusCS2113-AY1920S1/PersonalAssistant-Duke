package mocks;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;

public class ValidEmptySpec extends ArgSpec {
    private static final ValidEmptySpec spec = new ValidEmptySpec();

    public static ValidEmptySpec getSpec() {
        return spec;
    }

    private ValidEmptySpec() {
        cmdArgLevel = ArgLevel.OPTIONAL;
        initSwitches(
                new Switch("switch", String.class, true, ArgLevel.REQUIRED, "s"),
                new Switch("optswitch", String.class, true, ArgLevel.REQUIRED, "o")
        );
    }

    @Override
    protected void execute(DukeCore core) {
        // do nothing
    }
}
