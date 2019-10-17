package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;
import duke.data.Patient;

public class NewPatientCommand extends ArgCommand {

    @Override
    ArgSpec getSpec() {
        return NewPatientSpec.getSpec();
    };

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        core.patientMap.addPatient(new Patient(getSwitchVal("name"), getSwitchVal("bed"),
                getSwitchVal("allergies")));
        core.storage.writeJsonFile(core.patientMap.getPatientHashMap());
    }
}
