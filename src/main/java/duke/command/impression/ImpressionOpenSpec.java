package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.Switch;
import duke.data.DukeObject;
import duke.exception.DukeException;

public class ImpressionOpenSpec extends ImpressionObjSpec {

    private static final ImpressionOpenSpec spec = new ImpressionOpenSpec();

    public static ImpressionOpenSpec getSpec() {
        return spec;
    }

    private ImpressionOpenSpec() {
        cmdArgLevel = ArgLevel.OPTIONAL;
        initSwitches(
                new Switch("evidence", String.class, true, ArgLevel.REQUIRED, "e"),
                new Switch("treatment", String.class, true, ArgLevel.REQUIRED, "t")
        );
    }

    @Override
    protected void executeWithObj(DukeCore core, DukeObject obj) throws DukeException {
        core.uiContext.open(obj);
        core.updateUi("Accessing " + obj.getClass().getSimpleName() + " '" + obj.getName() + "'");
    }
}
