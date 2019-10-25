package duke.command;

import duke.DukeCore;
import duke.data.Impression;
import duke.data.Patient;
import duke.exception.DukeException;

public class PatientHistoryCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return PatientHistorySpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        Patient patient = (Patient) core.uiContext.getObject();
        patient.appendHistory(getSwitchVal("note"));
        core.ui.print("Note appended!");
        core.storage.writeJsonFile(core.patientMap.getPatientHashMap());
    }
}
