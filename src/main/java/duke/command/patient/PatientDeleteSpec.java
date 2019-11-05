package duke.command.patient;

import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;

public class PatientDeleteSpec extends ArgSpec {
    private static final PatientDeleteSpec spec = new PatientDeleteSpec();

    public static PatientDeleteSpec getSpec() {
        return spec;
    }

    private PatientDeleteSpec() {
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(
                new Switch("critical", String.class, true, ArgLevel.OPTIONAL, "c"),
                new Switch("investigation", String.class, true, ArgLevel.OPTIONAL, "in"),
                new Switch("impression", String.class, true, ArgLevel.OPTIONAL, "im"),
                new Switch("index", Integer.class, true, ArgLevel.REQUIRED, "i"),
                new Switch("name", String.class, true, ArgLevel.REQUIRED, "n")
        );
    }
}
