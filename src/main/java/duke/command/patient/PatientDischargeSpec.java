package duke.command.patient;

import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;

public class PatientDischargeSpec extends ArgSpec {
    private static final PatientDischargeSpec spec = new PatientDischargeSpec();

    public static PatientDischargeSpec getSpec() {
        return spec;
    }

    private PatientDischargeSpec() {
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(
                new Switch("bed", String.class, true, ArgLevel.REQUIRED, "b"),
                new Switch("index", Integer.class, true, ArgLevel.REQUIRED, "i"),
                new Switch("summary", String.class, true, ArgLevel.OPTIONAL, "sum")
        );
    }
}