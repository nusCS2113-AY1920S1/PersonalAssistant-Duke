package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;
import duke.data.DukeData;
import duke.data.Evidence;
import duke.data.Impression;
import duke.data.Treatment;
import duke.exception.DukeException;

public class ImpressionDeleteSpec extends ArgSpec {
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
    protected void execute(DukeCore core) throws DukeException {
        Impression impression = ImpressionUtils.getImpression(core);
        DukeData delData = ImpressionUtils.getData(cmd.getArg(), cmd.getSwitchVal("evidence"),
                cmd.getSwitchVal("treatment"), impression);
        String delMsg = "'" + delData.getName() + "' deleted!";
        if (delData instanceof Evidence) {
            impression.deleteEvidence(delData.getName());
        } else if (delData instanceof Treatment) {
            impression.deleteTreatment(delData.getName());
        }
        core.writeJsonFile();
        core.updateUi(delMsg);
    }
}
