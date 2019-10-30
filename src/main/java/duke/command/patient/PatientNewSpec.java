package duke.command.patient;

import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;

public class PatientNewSpec extends ArgSpec {

    private static final PatientNewSpec spec = new PatientNewSpec();

    public static PatientNewSpec getSpec() {
        return spec;
    }

    private PatientNewSpec() {
        emptyArgMsg = "You did not tell me anything about the impression you wish to add for the patient!";
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(
                new Switch("description", String.class, false, ArgLevel.REQUIRED, "desc")
        );
    }
}
