package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;
import duke.exception.DukeException;

public class ImpressionResultSpec extends ArgSpec {
    private static final ImpressionResultSpec spec = new ImpressionResultSpec();

    public static ImpressionResultSpec getSpec() {
        return spec;
    }

    private ImpressionResultSpec() {
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(
                new Switch("summary", String.class, true, ArgLevel.REQUIRED, "sum")
        );
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        //Impression impression = ImpressionUtils.getImpression(core);
        // TODO: find by name or index
    }
}
