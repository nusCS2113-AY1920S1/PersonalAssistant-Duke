package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.Switch;
import duke.data.DukeObject;
import duke.data.Evidence;
import duke.data.Impression;
import duke.data.Treatment;
import duke.exception.DukeException;

public class ImpressionDeleteSpec extends ImpressionObjSpec {
    private static final ImpressionDeleteSpec spec = new ImpressionDeleteSpec();

    public static ImpressionDeleteSpec getSpec() {
        return spec;
    }

    private ImpressionDeleteSpec() {
        cmdArgLevel = ArgLevel.OPTIONAL;
        initSwitches(
                new Switch("evidence", String.class, true, ArgLevel.REQUIRED, "e"),
                new Switch("treatment", String.class, true, ArgLevel.REQUIRED, "t")
        );
    }

    @Override
    protected void executeWithObj(DukeCore core, DukeObject obj) throws DukeException {
        Impression impression = ImpressionUtils.getImpression(core);
        String delMsg = "'" + obj.getName() + "' deleted!";
        if (obj instanceof Evidence) {
            impression.deleteEvidence(obj.getName());
        } else if (obj instanceof Treatment) {
            impression.deleteTreatment(obj.getName());
        }
        core.writeJsonFile();
        core.updateUi(delMsg);
    }
}
