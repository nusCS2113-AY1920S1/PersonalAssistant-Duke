package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ObjSpec;
import duke.command.Switch;
import duke.data.DukeObject;
import duke.data.Impression;
import duke.data.Investigation;
import duke.data.Medicine;
import duke.data.Plan;
import duke.data.Treatment;
import duke.exception.DukeException;
import duke.exception.DukeHelpException;

import java.util.List;

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
        DukeObject obj = ImpressionUtils.getData(null, null, cmd.getArg(), impression);
        if (obj == null) {
            processResults(core, ImpressionUtils.searchData(null, null, cmd.getArg(), impression));
        } else {
            executeWithObj(core, obj);
        }
    }

    @Override
    protected void executeWithObj(DukeCore core, DukeObject obj) throws DukeException {
        List<String> statusList;
        Treatment treatment = (Treatment) obj;
        Class targetClass = treatment.getClass(); //statics don't play nice with polymorphism
        if (targetClass == Medicine.class) {
            statusList = Medicine.getStatusArr();
        } else if (targetClass == Investigation.class) {
            statusList = Investigation.getStatusArr();
        } else if (targetClass == Plan.class) {
            statusList = Plan.getStatusArr();
        } else {
            throw new DukeException("This is not a recognised treatment!");
        }

        int status;
        String statusStr = cmd.getSwitchVal("set");
        if (statusStr == null) {
            status = treatment.getStatusIdx() + 1;
            if (status >= statusList.size()) {
                throw new DukeHelpException("This treatment cannot progress any further!", cmd);
            }
        } else {
            status = ImpressionUtils.processStatus(statusStr, statusList);
        }
        treatment.setStatusIdx(status);

        core.writeJsonFile();
        core.updateUi("Status of '" + treatment.getName() + "' updated to '" + statusList.get(status) + "'");
    }
}

