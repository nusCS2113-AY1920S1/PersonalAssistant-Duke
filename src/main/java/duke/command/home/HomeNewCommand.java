package duke.command.home;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.ui.context.Context;

public class HomeNewCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return HomeNewSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);

        //ideally, we would pass an array of objects
        nullToEmptyString(); //set optional string parameters to ""
        Integer height = switchToInt("height");
        Integer weight = switchToInt("weight");
        Integer age = switchToInt("age");
        Integer number = switchToInt("number");
        String bed = getSwitchVal("bed");
        String address = getSwitchVal("address");
        String history = getSwitchVal("history");
        // TODO: format checks for bed number?
        for (String existingBed : core.patientMap.getPatientObservableMap().keySet()) {
            if (existingBed.equals(bed)) {
                throw new DukeException("There is already a patient at that bed!");
            }
        }

        Patient patient = new Patient(getSwitchVal("name"), bed,
                getSwitchVal("allergies"), height, weight, age, number,
                address, history);
        core.patientMap.addPatient(patient);
        core.writeJsonFile();
        core.updateUi("Patient added.");

        if (isSwitchSet("go")) {
            core.uiContext.setContext(Context.PATIENT, patient);
        }
    }
}
