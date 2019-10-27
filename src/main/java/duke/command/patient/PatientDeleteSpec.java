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
        emptyArgMsg = "You didn't tell me anything about what to delete!";
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(
                new Switch("critical", String.class, true, ArgLevel.OPTIONAL, "c"),
                new Switch("investigation", String.class, true, ArgLevel.OPTIONAL, "i"),
                new Switch("impression", String.class, true, ArgLevel.OPTIONAL, "im")
        );
    }
}
