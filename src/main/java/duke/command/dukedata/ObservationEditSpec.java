package duke.command.dukedata;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;
import duke.command.impression.ImpressionUtils;
import duke.data.Observation;
import duke.exception.DukeException;

public class ObservationEditSpec extends ArgSpec {
    private static final ObservationEditSpec spec = new ObservationEditSpec();

    public static ObservationEditSpec getSpec() {
        return spec;
    }

    private ObservationEditSpec() {
        cmdArgLevel = ArgLevel.OPTIONAL;
        initSwitches(
                new Switch("append", null, true, ArgLevel.NONE, "a"),
                new Switch("name", String.class, true, ArgLevel.REQUIRED, "n"),
                new Switch("priority", Integer.class, true, ArgLevel.REQUIRED, "pri"),
                new Switch("objective", null, true, ArgLevel.NONE, "obj"),
                new Switch("summary", String.class, true, ArgLevel.REQUIRED, "sum")
        );
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        ImpressionUtils.editData(core, cmd, (Observation) core.uiContext.getObject());
    }
}
