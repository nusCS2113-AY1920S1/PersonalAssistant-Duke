package duke.command;

import duke.DukeCore;
import duke.data.Patient;
import duke.exception.DukeException;

public class NewPatientCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return NewPatientSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        Integer height = CommandHelpers.toInt(getSwitchVal("height"));
        Integer weight = CommandHelpers.toInt(getSwitchVal("weight"));
        Integer age = CommandHelpers.toInt(getSwitchVal("age"));
        Integer number = CommandHelpers.toInt(getSwitchVal("number"));
        core.patientMap.addPatient(new Patient(getSwitchVal("name"), getSwitchVal("bed"),
                getSwitchVal("allergies"), height, weight, age, number,
                getSwitchVal("address"), getSwitchVal("history")));
        core.storage.writeJsonFile(core.patientMap.getPatientHashMap());
    }
}
