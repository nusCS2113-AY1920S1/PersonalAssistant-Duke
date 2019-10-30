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
        emptyArgMsg = "You did not tell me what I should open!";
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(
                new Switch("impression", String.class, true, ArgLevel.NONE, "im"),
                new Switch("critical", String.class, true, ArgLevel.NONE, "c"),
                new Switch("investigation", String.class, true, ArgLevel.NONE, "inv"),
                new Switch("index", Integer.class, true, ArgLevel.REQUIRED, "i"),
                new Switch("name", String.class, true, ArgLevel.REQUIRED, "n")
        );
    }
}
