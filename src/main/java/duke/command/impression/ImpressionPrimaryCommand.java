package duke.command.impression;

import duke.DukeCore;
import duke.command.Command;
import duke.data.Patient;
import duke.exception.DukeException;

import static duke.command.impression.ImpressionHelpers.getImpression;
import static duke.command.impression.ImpressionHelpers.getPatient;

public class ImpressionPrimaryCommand extends Command {

    @Override
    public void execute(DukeCore core) throws DukeException {
        Patient patient = getPatient(getImpression(core));
        // TODO: create method in patient to set primary impression
    }
}
