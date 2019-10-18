package mocks;

import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;

public class DoctorSpec extends ArgSpec {

    private static final DoctorSpec spec = new DoctorSpec();

    public static DoctorSpec getSpec() {
        return spec;
    }

    private DoctorSpec() {
        emptyArgMsg = "You didn't tell me what to do!";
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(
               new Switch("switch", String.class, false, ArgLevel.REQUIRED, "s"),
               new Switch("optswitch", String.class, true, ArgLevel.REQUIRED, "o")
        );
    }
}
