package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.data.DukeData;
import duke.data.Impression;
import duke.data.Investigation;
import duke.data.Medicine;
import duke.data.Plan;
import duke.data.Treatment;
import duke.exception.DukeException;

import java.util.List;

public class ImpressionStatusCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return ImpressionStatusSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        Impression impression = ImpressionHelpers.getImpression(core);
        Treatment treatment = ImpressionHelpers.findTreatment(impression, getArg());
        List<String> statusList;
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
        String statusStr = getSwitchVal("set");
        if (statusStr == null) {
            status = treatment.getStatusIdx() + 1;
        } else {
            status = ImpressionHelpers.processStatus(statusStr, statusList);
        }
        treatment.setStatusIdx(status);

        core.writeJsonFile();
        core.ui.print("Status of '" + treatment.getName() + "' updated to '" + statusList.get(status) + "'");
    }
}
