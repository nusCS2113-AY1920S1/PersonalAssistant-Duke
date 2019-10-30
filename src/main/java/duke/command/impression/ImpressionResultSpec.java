package duke.command.impression;

import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;

public class ImpressionResultSpec extends ArgSpec {
    private static final ImpressionResultSpec spec = new ImpressionResultSpec();

    public static ImpressionResultSpec getSpec() {
        return spec;
    }

    private ImpressionResultSpec() {
        emptyArgMsg = "You didn't tell me anything about the investigation you want to convert to a result!";
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(
                new Switch("summary", String.class, true, ArgLevel.REQUIRED, "sum")
        );
    }
}
