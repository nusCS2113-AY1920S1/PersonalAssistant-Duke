package duke.command;

import duke.DukeCore;
import duke.data.Patient;
import duke.exception.DukeException;

public class HomeNewCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return HomeNewSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        //ideally, we would pass an array of objects
        Integer height, weight, age, number;
        height = CommandHelpers.switchToInt("height", this);
        weight = CommandHelpers.switchToInt("weight", this);
        age = CommandHelpers.switchToInt("age", this);
        number = CommandHelpers.switchToInt("number", this);
        String bed = getSwitchVal("bed");
        if (core.patientMap.getPatientHashMap().containsKey(bed)) {
            throw new DukeException("There is already a patient at that bed!");
        }
        core.patientMap.addPatient(new Patient(getSwitchVal("name"), bed,
                getSwitchVal("allergies"), height, weight, age, number,
                getSwitchVal("address"), getSwitchVal("history")));
        core.storage.writeJsonFile(core.patientMap.getPatientHashMap());
    }
}
