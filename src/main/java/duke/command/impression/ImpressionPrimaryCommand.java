package duke.command.impression;

import duke.DukeCore;
import duke.command.Command;
import duke.data.Impression;
import duke.data.Patient;
import duke.exception.DukeException;

import static duke.command.impression.ImpressionHelpers.getImpression;
import static duke.command.impression.ImpressionHelpers.getPatient;

public class ImpressionPrimaryCommand extends Command {

    @Override
    public void execute(DukeCore core) throws DukeException {
        Impression impression = getImpression(core);
        Patient patient = getPatient(impression);
        patient.setPrimaryDiagnosis(impression.getName());
    }
}
