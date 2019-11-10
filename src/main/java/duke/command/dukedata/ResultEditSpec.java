package duke.command.dukedata;

import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;

public class ResultEditSpec extends ArgSpec {

    private static final ResultEditSpec spec = new ResultEditSpec();

    public static ResultEditSpec getSpec() {
        return spec;
    }

    private ResultEditSpec() {
        cmdArgLevel = ArgLevel.OPTIONAL;
        initSwitches(
                new Switch("append", null, true, ArgLevel.NONE, "a"),
                new Switch("name", String.class, true, ArgLevel.REQUIRED, "n"),
                new Switch("priority", Integer.class, true, ArgLevel.REQUIRED, "pri"),
                new Switch("summary", String.class, true, ArgLevel.REQUIRED, "sum")
        );
    }
}
