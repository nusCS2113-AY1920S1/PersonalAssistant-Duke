package duke.command.home;

import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;

public class HomeHistorySpec extends ArgSpec {
    private static final HomeHistorySpec spec = new HomeHistorySpec();

    public static HomeHistorySpec getSpec() {
        return spec;
    }

    private HomeHistorySpec() {
        emptyArgMsg = "You didn't tell me anything about the patient!";
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(
                new Switch("bed", String.class, true, ArgLevel.REQUIRED, "b"),
                new Switch("index", Integer.class, true, ArgLevel.REQUIRED, "i"),
                new Switch("message", String.class, false, ArgLevel.REQUIRED, "m"),
                new Switch("rewrite", String.class, true, ArgLevel.REQUIRED, "r")
        );
    }
}
