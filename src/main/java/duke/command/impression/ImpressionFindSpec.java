package duke.command.impression;

import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;

public class ImpressionFindSpec extends ArgSpec {

    private static final ImpressionFindSpec spec = new ImpressionFindSpec();

    public static ImpressionFindSpec getSpec() {
        return spec;
    }

    private ImpressionFindSpec() {
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(
                new Switch("evidence", String.class, true, ArgLevel.NONE, "e"),
                new Switch("treatment", String.class, true, ArgLevel.NONE, "t")
        );
    }
}
