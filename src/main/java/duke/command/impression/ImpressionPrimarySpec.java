package duke.command.impression;

import duke.DukeCore;
import duke.command.CommandSpec;
import duke.data.Impression;
import duke.data.Patient;
import duke.exception.DukeException;

public class ImpressionPrimarySpec extends CommandSpec {
    private static final ImpressionPrimarySpec spec = new ImpressionPrimarySpec();

    public static ImpressionPrimarySpec getSpec() {
        return spec;
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        Impression impression = ImpressionUtils.getImpression(core);
        Patient patient = impression.getParent();
        patient.setPrimaryDiagnosis(impression.getName());
        core.writeJsonFile();
        core.updateUi("Updated " + patient.getName() + "'s primary diagnosis to '" + impression.getName() + "'!");
    }
}
