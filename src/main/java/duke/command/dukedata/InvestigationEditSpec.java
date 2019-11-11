package duke.command.dukedata;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;
import duke.command.impression.ImpressionUtils;
import duke.data.Investigation;
import duke.exception.DukeException;

public class InvestigationEditSpec extends ArgSpec {
    private static final InvestigationEditSpec spec = new InvestigationEditSpec();

    public static InvestigationEditSpec getSpec() {
        return spec;
    }

    private InvestigationEditSpec() {
        cmdArgLevel = ArgLevel.OPTIONAL;
        initSwitches(
                new Switch("append", null, true, ArgLevel.NONE, "a"),
                new Switch("name", String.class, true, ArgLevel.REQUIRED, "n"),
                new Switch("priority", Integer.class, true, ArgLevel.REQUIRED, "pri"),
                new Switch("status", String.class, true, ArgLevel.REQUIRED, "sta"),
                new Switch("summary", String.class, true, ArgLevel.REQUIRED, "sum")
        );
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        ImpressionUtils.editData(core, cmd, (Investigation) core.uiContext.getObject());
    }
}
