package duke.command;

import duke.DukeCore;
import duke.data.Patient;
import duke.exception.DukeException;

public class PatientEditCommand extends ArgCommand {
    @Override
    protected ArgSpec getSpec() {
        return PatientEditSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);

        Patient patient = (Patient) core.uiContext.getObject();

        // TODO: refactor
        // TODO: Ability to change bed number and name
        // TODO: append
        int height = switchToInt("height");
        if (height != 0) {
            patient.setHeight(height);
        }

        int weight = switchToInt("weight");
        if (weight != 0) {
            patient.setWeight(weight);
        }

        int age = switchToInt("age");
        if (age != 0) {
            patient.setAge(age);
        }

        int number = switchToInt("number");
        if (number != 0) {
            patient.setNumber(number);
        }

        patient.setAddress(getSwitchVal("address"));
        patient.setHistory(getSwitchVal("history"));
        patient.setAllergies(getSwitchVal("allergies"));

        patient.updateAttributes();
        core.ui.print("Edited details of patient!");
        core.storage.writeJsonFile(core.patientMap.getPatientHashMap());
    }
}
