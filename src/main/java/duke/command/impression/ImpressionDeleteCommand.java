package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.data.DukeData;
import duke.data.Evidence;
import duke.data.Impression;
import duke.data.Treatment;
import duke.exception.DukeException;

import static duke.command.impression.ImpressionHelpers.findVarTypeData;
import static duke.command.impression.ImpressionHelpers.getImpression;

public class ImpressionDeleteCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return ImpressionDeleteSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        Impression impression = getImpression(core);
        DukeData delData = findVarTypeData(getArg(), getSwitchVal("evidence"),
                getSwitchVal("treatment"), impression, this);
        String delMsg = "'" + delData.getName() + "' deleted!";
        if (delData instanceof Evidence) {
            impression.deleteEvidence(delData.getName());
        } else if (delData instanceof Treatment) {
            impression.deleteTreatment(delData.getName());
        }
        core.writeJsonFile();
        core.ui.print(delMsg);
    }
}
