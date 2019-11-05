package duke.command.home;

import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;

public class HomeFindSpec extends ArgSpec {

    private static final HomeFindSpec spec = new HomeFindSpec();

    public static HomeFindSpec getSpec() {
        return spec;
    }

    private HomeFindSpec() {
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(
                new Switch("patient", String.class, true, ArgLevel.NONE, "p"),
                new Switch("impression", String.class, true, ArgLevel.NONE, "i"),
                new Switch("evidence", String.class, true, ArgLevel.NONE, "e"),
                new Switch("treatment", String.class, true, ArgLevel.NONE, "t")
        );
    }
}
