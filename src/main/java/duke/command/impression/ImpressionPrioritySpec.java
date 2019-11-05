package duke.command.impression;

import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;

public class ImpressionPrioritySpec extends ArgSpec {
    private static final ImpressionPrioritySpec spec = new ImpressionPrioritySpec();

    public static ImpressionPrioritySpec getSpec() {
        return spec;
    }

    private ImpressionPrioritySpec() {
        cmdArgLevel = ArgLevel.OPTIONAL;
        initSwitches(
                new Switch("evidence", String.class, true, ArgLevel.REQUIRED, "e"),
                new Switch("treatment", String.class, true, ArgLevel.REQUIRED, "t"),
                new Switch("set", Integer.class, false, ArgLevel.REQUIRED, "s")
        );
    }
}
