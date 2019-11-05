package duke.command.patient;

import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;

public class PatientOpenSpec extends ArgSpec {
    private static final PatientOpenSpec spec = new PatientOpenSpec();

    public static PatientOpenSpec getSpec() {
        return spec;
    }

    private PatientOpenSpec() {
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(
                new Switch("impression", String.class, true, ArgLevel.NONE, "im"),
                new Switch("critical", String.class, true, ArgLevel.NONE, "c"),
                new Switch("investigation", String.class, true, ArgLevel.NONE, "inv")
        );
    }
}
