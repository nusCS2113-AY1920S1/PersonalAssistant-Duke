package duke.command.impression;

import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;

public class ImpressionMoveSpec extends ArgSpec {
    private static final ImpressionMoveSpec spec = new ImpressionMoveSpec();

    public static ImpressionMoveSpec getSpec() {
        return spec;
    }

    private ImpressionMoveSpec() {
        cmdArgLevel = ArgLevel.OPTIONAL;
        initSwitches(
                new Switch("evidence", String.class, true, ArgLevel.REQUIRED, "e"),
                new Switch("treatment", String.class, true, ArgLevel.REQUIRED, "t"),
                new Switch("impression", String.class, true, ArgLevel.REQUIRED, "im")
        );
    }
}
