package duke.command.home;

import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;

public class HomeOpenSpec extends ArgSpec {
    private static final HomeOpenSpec spec = new HomeOpenSpec();

    public static HomeOpenSpec getSpec() {
        return spec;
    }

    private HomeOpenSpec() {
        emptyArgMsg = "You did not tell me anything about the patient you wish to access!";
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(
                new Switch("bed", String.class, true, ArgLevel.REQUIRED, "b"),
                new Switch("index", Integer.class, true, ArgLevel.REQUIRED, "i"),
                new Switch("impression", String.class, true, ArgLevel.NONE, "im")
        );
    }
}
