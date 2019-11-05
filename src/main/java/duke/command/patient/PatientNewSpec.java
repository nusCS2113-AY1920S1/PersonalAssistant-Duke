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
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(
                new Switch("name", String.class, false, ArgLevel.REQUIRED, "n"),
                new Switch("description", String.class, false, ArgLevel.REQUIRED, "desc")
        );
    }
}
