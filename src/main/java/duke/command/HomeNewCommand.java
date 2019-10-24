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
        Integer height = CommandHelpers.switchToInt("height", this);
        Integer weight = CommandHelpers.switchToInt("weight", this);
        Integer age = CommandHelpers.switchToInt("age", this);
        Integer number = CommandHelpers.switchToInt("number", this);
        String bed = getSwitchVal("bed");
        // TODO: format checks for bed number?
        for (String existingBed : core.patientMap.getPatientObservableMap().keySet()) {
            if (existingBed.equals(bed)) {
                throw new DukeException("There is already a patient at that bed!");
            }
        }
        core.patientMap.addPatient(new Patient(getSwitchVal("name"), bed,
                getSwitchVal("allergies"), height, weight, age, number,
                getSwitchVal("address"), getSwitchVal("history")));
        core.storage.writeJsonFile(core.patientMap.getPatientHashMap());
    }
}
