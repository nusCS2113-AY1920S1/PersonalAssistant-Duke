package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.Switch;
import duke.data.DukeData;
import duke.data.DukeObject;
import duke.exception.DukeException;

public class ImpressionPrioritySpec extends ImpressionObjSpec {
    private static final ImpressionPrioritySpec spec = new ImpressionPrioritySpec();

    public static ImpressionPrioritySpec getSpec() {
        return spec;
    }

    private ImpressionPrioritySpec() {
        cmdArgLevel = ArgLevel.OPTIONAL;
        initSwitches(
                new Switch("evidence", String.class, true, ArgLevel.REQUIRED, "e"),
                new Switch("treatment", String.class, true, ArgLevel.REQUIRED, "t"),
                new Switch("set", Integer.class, false, ArgLevel.REQUIRED, "s")
        );
    }

    @Override
    protected void executeWithObj(DukeCore core, DukeObject obj) throws DukeException {
        DukeData prioData = (DukeData) obj;
        int priority = cmd.switchToInt("set");
        ImpressionUtils.checkPriority(priority);
        prioData.setPriority(priority);
        core.writeJsonFile();
        core.updateUi("Updated '" + prioData.getName() + "' priority to " + priority + "!");
    }
}
