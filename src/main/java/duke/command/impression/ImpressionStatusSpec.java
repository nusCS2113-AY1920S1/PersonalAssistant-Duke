package duke.command.impression;

import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;

public class ImpressionStatusSpec extends ArgSpec {
    private static final ImpressionStatusSpec spec = new ImpressionStatusSpec();

    public static ImpressionStatusSpec getSpec() {
        return spec;
    }

    private ImpressionStatusSpec() {
        emptyArgMsg = "You didn't tell me anything about the treatment whose status you want to change!";
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(
                new Switch("set", Integer.class, true, ArgLevel.REQUIRED, "s")
        );
    }
}

