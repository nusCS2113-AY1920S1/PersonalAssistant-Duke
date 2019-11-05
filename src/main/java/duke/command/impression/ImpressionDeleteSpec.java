package duke.command.impression;

import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;

public class ImpressionDeleteSpec extends ArgSpec {
    private static final ImpressionDeleteSpec spec = new ImpressionDeleteSpec();

    public static ImpressionDeleteSpec getSpec() {
        return spec;
    }

    private ImpressionDeleteSpec() {
        cmdArgLevel = ArgLevel.OPTIONAL;
        initSwitches(
                new Switch("evidence", String.class, true, ArgLevel.REQUIRED, "e"),
                new Switch("treatment", String.class, true, ArgLevel.REQUIRED, "t")
        );
    }
}
