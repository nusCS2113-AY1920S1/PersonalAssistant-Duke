package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ObjSpec;
import duke.command.Switch;
import duke.data.DukeObject;
import duke.data.Impression;
import duke.data.Treatment;
import duke.exception.DukeException;
import duke.exception.DukeHelpException;

public class ImpressionStatusSpec extends ObjSpec {
    private static final ImpressionStatusSpec spec = new ImpressionStatusSpec();

    public static ImpressionStatusSpec getSpec() {
        return spec;
    }

    private ImpressionStatusSpec() {
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(
                new Switch("set", Integer.class, true, ArgLevel.REQUIRED, "s")
        );
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        super.execute(core);
        Impression impression = ImpressionUtils.getImpression(core);
        DukeObject obj = ImpressionUtils.getDataByIdx(null, null, cmd.getArg(), impression);
        if (obj == null) {
            processResults(core, ImpressionUtils.searchData(null, null, cmd.getArg(), impression));
        } else {
            executeWithObj(core, obj);
        }
    }

    @Override
    protected void executeWithObj(DukeCore core, DukeObject obj) throws DukeException {
        Treatment treatment = (Treatment) obj;
        int status;
        String statusStr = cmd.getSwitchVal("set");
        if (statusStr == null) {
            status = treatment.getStatusIdx() + 1;
            if (status >= treatment.getStatusArr().size()) {
                throw new DukeHelpException("This treatment cannot progress any further!", cmd);
            }
            treatment.setStatus(status);
        } else {
            treatment.setStatus(statusStr);
        }

        core.writeJsonFile();
        core.updateUi("Status of '" + treatment.getName() + "' updated to '" + treatment.getStatusStr() + "'");
    }
}

