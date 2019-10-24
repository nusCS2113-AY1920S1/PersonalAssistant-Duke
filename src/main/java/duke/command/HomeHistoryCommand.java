package duke.command;

import duke.DukeCore;
import duke.data.Patient;
import duke.exception.DukeException;

public class HomeHistoryCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return HomeHistorySpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        if (core.patientMap.patientExist(getArg())) {
            String patientDetails = getArg() + ", " + core.patientMap.getPatient(getArg()).getName();
            String message = getSwitchVal("message");
            String rewrite = getSwitchVal("rewrite");
            String newHistory = message;
            if (rewrite != null && (rewrite.toLowerCase().equals("y")
                    || rewrite.toLowerCase().equals("yes") || rewrite.toLowerCase().equals("ye"))) {
                core.patientMap.getPatient(getArg()).setHistory(message);
            } else {
                newHistory = core.patientMap.getPatient(getArg()).appendHistory(message);
            }
            core.storage.writeJsonFile(core.patientMap.getPatientHashMap());
            core.ui.print("History of " + patientDetails + " updated with:\n" + newHistory + "\n");
        } else {
            throw new DukeException("No such Patient!");
        }
    }
}
