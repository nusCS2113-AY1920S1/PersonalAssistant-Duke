package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ObjSpec;
import duke.command.Switch;
import duke.data.DukeObject;
import duke.data.Impression;
import duke.data.Investigation;
import duke.exception.DukeException;

public class ImpressionResultSpec extends ObjSpec {
    private static final ImpressionResultSpec spec = new ImpressionResultSpec();

    public static ImpressionResultSpec getSpec() {
        return spec;
    }

    private ImpressionResultSpec() {
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(
                new Switch("summary", String.class, true, ArgLevel.REQUIRED, "sum")
        );
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        super.execute(core);
        Impression impression = ImpressionUtils.getImpression(core);
        DukeObject obj = ImpressionUtils.getData(null, null, cmd.getArg(), impression);
        if (obj == null) {
            processResults(core, ImpressionUtils.searchData(null, null, cmd.getArg(), impression));
        } else if (!(obj instanceof Investigation)) {
            throw new DukeException("Only investigations can be converted into results!");
        } else {
            executeWithObj(core, obj);
        }
    }


    @Override
    protected void executeWithObj(DukeCore core, DukeObject obj) throws DukeException {
        Impression impression = ImpressionUtils.getImpression(core);
        Investigation invx = (Investigation) obj;
        impression.addNewEvidence(invx.toResult(cmd.getSwitchVal("summary")));
        impression.deleteTreatment(obj.getName());
        core.ui.updateUi("Converted the '" + obj.getName() + "' investigation to a report!");
    }
}
