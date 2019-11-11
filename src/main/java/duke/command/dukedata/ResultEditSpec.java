package duke.command.dukedata;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;
import duke.command.impression.ImpressionUtils;
import duke.data.Result;
import duke.exception.DukeException;

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

    @Override
    protected void execute(DukeCore core) throws DukeException {
        ImpressionUtils.editData(core, cmd, (Result) core.uiContext.getObject());
    }
}
