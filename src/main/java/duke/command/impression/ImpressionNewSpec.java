package duke.command.impression;

import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;

public class ImpressionNewSpec extends ArgSpec {

    private static final ImpressionNewSpec spec = new ImpressionNewSpec();

    public static ImpressionNewSpec getSpec() {
        return spec;
    }

    private ImpressionNewSpec() {
        emptyArgMsg = "You didn't tell me anything about the new data to enter!";
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(
                new Switch("medicine", null, true, ArgLevel.NONE, "m"),
                new Switch("investigation", null, true, ArgLevel.NONE, "i", "invx"),
                new Switch("plan", null, true, ArgLevel.NONE, "p"),
                new Switch("observation", null, true, ArgLevel.NONE, "o"),
                new Switch("result", null, true, ArgLevel.NONE, "r"),
                new Switch("go", null, true, ArgLevel.NONE, "g"),
                new Switch("name", String.class, false, ArgLevel.REQUIRED, "n"),
                new Switch("status", String.class, true, ArgLevel.REQUIRED, "sta"),
                new Switch("priority", Integer.class, true, ArgLevel.REQUIRED, "pri"),
                new Switch("summary", String.class, true, ArgLevel.REQUIRED, "sum"),
                new Switch("dose", String.class, true, ArgLevel.REQUIRED, "d"),
                new Switch("date", String.class, true, ArgLevel.REQUIRED, "da"),
                new Switch("duration", String.class, true, ArgLevel.REQUIRED, "du"),
                new Switch("subjective", null, true, ArgLevel.NONE, "sub"),
                new Switch("objective", null, true, ArgLevel.NONE, "obj")
        );
    }
}
