package duke.command.home;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.ui.context.Context;

public class HomeNewSpec extends ArgSpec {
    private static final HomeNewSpec spec = new HomeNewSpec();

    public static HomeNewSpec getSpec() {
        return spec;
    }

    private HomeNewSpec() {
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(
                new Switch("name", String.class, false, ArgLevel.REQUIRED, "n"),
                new Switch("bed", String.class, false, ArgLevel.REQUIRED, "b"),
                new Switch("allergies", String.class, false, ArgLevel.REQUIRED, "a",
                        "allergy"),
                new Switch("height", Integer.class, true, ArgLevel.REQUIRED, "h"),
                new Switch("weight", Integer.class, true, ArgLevel.REQUIRED, "w"),
                new Switch("age", Integer.class, true, ArgLevel.REQUIRED, "ag"),
                new Switch("number", Integer.class, true, ArgLevel.REQUIRED, "num"),
                new Switch("address", String.class, true, ArgLevel.REQUIRED, "ad"),
                new Switch("history", String.class, true, ArgLevel.REQUIRED, "hi"),
                new Switch("go", String.class, true, ArgLevel.NONE, "g")
        );
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        //ideally, we would pass an array of objects
        cmd.nullToEmptyString(); //set optional string parameters to ""
        Integer height = cmd.switchToInt("height");
        Integer weight = cmd.switchToInt("weight");
        Integer age = cmd.switchToInt("age");
        Integer number = cmd.switchToInt("number");
        String bed = cmd.getSwitchVal("bed");
        String address = cmd.getSwitchVal("address");
        String history = cmd.getSwitchVal("history");
        // TODO: format checks for bed number?
        for (String existingBed : core.patientMap.getPatientObservableMap().keySet()) {
            if (existingBed.equals(bed)) {
                throw new DukeException("There is already a patient at that bed!");
            }
        }

        Patient patient = new Patient(cmd.getSwitchVal("name"), bed,
                cmd.getSwitchVal("allergies"), height, weight, age, number,
                address, history);
        core.patientMap.addPatient(patient);
        core.writeJsonFile();
        core.updateUi("Patient added.");

        if (cmd.isSwitchSet("go")) {
            core.uiContext.setContext(Context.PATIENT, patient);
        }
    }
}
