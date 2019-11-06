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
                // TODO: Changes need to be made to Patient class. Update section in User Guide
                // new Switch("name", String.class, true, ArgLevel.REQUIRED, "n"),
                // new Switch("bed", String.class, true, ArgLevel.OPTIONAL, "b"),
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
        Patient patient = (Patient) core.uiContext.getObject();
        boolean append = cmd.isSwitchSet("append");

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

        patient.updateAttributes();
        core.writeJsonFile();
        core.updateUi("Edited specified details of patient!");
    }
}
