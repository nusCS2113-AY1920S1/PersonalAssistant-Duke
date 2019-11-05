package duke.command.impression;

import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;

public class ImpressionEditSpec extends ArgSpec {

    private static final ImpressionEditSpec spec = new ImpressionEditSpec();

    public static ImpressionEditSpec getSpec() {
        return spec;
    }

    private ImpressionEditSpec() {
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(
                new Switch("append", null, true, ArgLevel.NONE, "a"),
                new Switch("medicine", null, true, ArgLevel.REQUIRED, "m"),
                new Switch("investigation", null, true, ArgLevel.REQUIRED, "i", "invx"),
                new Switch("plan", null, true, ArgLevel.REQUIRED, "p"),
                new Switch("observation", null, true, ArgLevel.REQUIRED, "o"),
                new Switch("result", null, true, ArgLevel.NONE, "r"),
                new Switch("name", String.class, true, ArgLevel.REQUIRED, "n"),
                new Switch("status", String.class, true, ArgLevel.REQUIRED, "sta"),
                new Switch("priority", Integer.class, true, ArgLevel.REQUIRED, "pri"),
                new Switch("summary", String.class, true, ArgLevel.REQUIRED, "sum"),
                new Switch("dose", String.class, true, ArgLevel.REQUIRED, "d"),
                new Switch("date", String.class, true, ArgLevel.REQUIRED, "da"),
                new Switch("duration", String.class, true, ArgLevel.REQUIRED, "du"),
                new Switch("subjective", null, true, ArgLevel.NONE, "sub"),
                new Switch("objective", null, true, ArgLevel.NONE, "obj"),
                new Switch("description", String.class, true, ArgLevel.REQUIRED, "desc")
        );
    }
}
