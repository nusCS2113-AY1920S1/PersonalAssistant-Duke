package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;
import duke.data.Patient;
import duke.exception.DukeException;

public class PatientEditSpec extends ArgSpec {
    private static final PatientEditSpec spec = new PatientEditSpec();

    public static PatientEditSpec getSpec() {
        return spec;
    }

    private PatientEditSpec() {
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(
                new Switch("name", String.class, true, ArgLevel.REQUIRED, "n"),
                new Switch("bed", String.class, true, ArgLevel.REQUIRED, "b"),
                new Switch("allergies", String.class, true, ArgLevel.REQUIRED, "a",
                        "allergy"),
                new Switch("height", Integer.class, true, ArgLevel.REQUIRED, "h"),
                new Switch("weight", Integer.class, true, ArgLevel.REQUIRED, "w"),
                new Switch("age", Integer.class, true, ArgLevel.REQUIRED, "ag"),
                new Switch("number", Integer.class, true, ArgLevel.REQUIRED, "num"),
                new Switch("address", String.class, true, ArgLevel.REQUIRED, "ad"),
                new Switch("history", String.class, true, ArgLevel.REQUIRED, "hi"),
                new Switch("append", String.class, true, ArgLevel.NONE, "app")
        );
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        super.execute(core);
        Patient patient = (Patient) core.uiContext.getObject();
        boolean append = cmd.isSwitchSet("append");

        String name = cmd.getSwitchVal("name");
        if (name != null) {
            patient.setName(append ? (patient.getName() + " " + name) : name);
        }

        String bedNo = cmd.getSwitchVal("bed");
        if (bedNo != null) {
            String newBed = (append) ? (patient.getBedNo() + " " + bedNo) : bedNo;
            if (core.patientData.getPatientByBed(newBed) != null) {
                throw new DukeException("There is already a patient at that bed!");
            }
            patient.setBedNo(newBed);
        }

        int height = cmd.switchToInt("height");
        if (height != -1) {
            patient.setHeight(height);
        }

        int weight = cmd.switchToInt("weight");
        if (weight != -1) {
            patient.setWeight(weight);
        }

        int age = cmd.switchToInt("age");
        if (age != -1) {
            patient.setAge(age);
        }

        int number = cmd.switchToInt("number");
        if (number != -1) {
            patient.setNumber(number);
        }

        String address = cmd.getSwitchVal("address");
        if (address != null) {
            patient.setAddress(append ? (patient.getAddress() + " " + address) : address);
        }

        String history = cmd.getSwitchVal("history");
        if (history != null) {
            patient.setHistory(append ? (patient.getHistory() + " " + history) : history);
        }

        String allergies = cmd.getSwitchVal("allergies");
        if (allergies != null) {
            patient.setAllergies(append ? (patient.getAllergies() + ", " + allergies) : allergies);
        }

        core.writeJsonFile();
        core.updateUi("Edited specified details of patient!");
    }
}
