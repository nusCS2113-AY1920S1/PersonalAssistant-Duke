package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
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
        boolean append = isSwitchSet("append");

        int height = switchToInt("height");
        if (height != -1) {
            patient.setHeight(height);
        }

        int weight = switchToInt("weight");
        if (weight != -1) {
            patient.setWeight(weight);
        }

        int age = switchToInt("age");
        if (age != -1) {
            patient.setAge(age);
        }

        int number = switchToInt("number");
        if (number != -1) {
            patient.setNumber(number);
        }

        String address = getSwitchVal("address");
        if (address != null) {
            patient.setAddress(append ? (patient.getAddress() + " " + address) : address);
        }

        String history = getSwitchVal("history");
        if (history != null) {
            patient.setHistory(append ? (patient.getHistory() + " " + history) : history);
        }

        String allergies = getSwitchVal("allergies");
        if (allergies != null) {
            patient.setAllergies(append ? (patient.getAllergies() + ", " + allergies) : allergies);
        }

        patient.updateAttributes();
        core.writeJsonFile();
        core.ui.print("Edited specified details of patient!");
    }
}
