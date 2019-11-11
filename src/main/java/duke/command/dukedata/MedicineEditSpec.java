package duke.command.dukedata;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;
import duke.command.impression.ImpressionUtils;
import duke.data.Medicine;
import duke.exception.DukeException;

public class MedicineEditSpec extends ArgSpec {

    private static final MedicineEditSpec spec = new MedicineEditSpec();

    public static MedicineEditSpec getSpec() {
        return spec;
    }

    private MedicineEditSpec() {
        cmdArgLevel = ArgLevel.OPTIONAL;
        initSwitches(
                new Switch("append", null, true, ArgLevel.NONE, "a"),
                new Switch("name", String.class, true, ArgLevel.REQUIRED, "n"),
                new Switch("priority", Integer.class, true, ArgLevel.REQUIRED, "pri"),
                new Switch("dose", String.class, true, ArgLevel.REQUIRED, "d"),
                new Switch("date", String.class, true, ArgLevel.REQUIRED, "da"),
                new Switch("duration", String.class, true, ArgLevel.REQUIRED, "du"),
                new Switch("status", String.class, true, ArgLevel.REQUIRED, "sta")
        );
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        ImpressionUtils.editData(core, cmd, (Medicine) core.uiContext.getObject());
    }
}
