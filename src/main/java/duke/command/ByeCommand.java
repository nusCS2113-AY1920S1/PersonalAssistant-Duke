package duke.command;

import duke.DukeCore;
import duke.exception.DukeFatalException;

public class ByeCommand extends Command {
    @Override
    public void execute(DukeCore core) throws DukeFatalException {
        core.storage.writeJsonFile(core.patientMap.getPatientHashMap());
        core.stop();
    }
}
