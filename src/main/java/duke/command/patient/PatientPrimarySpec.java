package duke.command.patient;

import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;

public class PatientPrimarySpec extends ArgSpec {
    private static final PatientPrimarySpec spec = new PatientPrimarySpec();

    public static PatientPrimarySpec getSpec() {
        return spec;
    }

    private PatientPrimarySpec() {
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(
                new Switch("index", Integer.class, true, ArgLevel.REQUIRED, "i"),
                new Switch("name", String.class, true, ArgLevel.REQUIRED, "n")
        );
    }
}
