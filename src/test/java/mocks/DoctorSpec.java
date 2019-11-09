package mocks;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;

public class DoctorSpec extends ArgSpec {

    private static final DoctorSpec spec = new DoctorSpec();

    public static DoctorSpec getSpec() {
        return spec;
    }

    private DoctorSpec() {
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(
                new Switch("switch", String.class, false, ArgLevel.REQUIRED, "s"),
                new Switch("optswitch", String.class, true, ArgLevel.REQUIRED, "o"),
                new Switch("maybe", String.class, true, ArgLevel.OPTIONAL, "m"),
                new Switch("none", String.class, true, ArgLevel.NONE, "n")
        );
    }

    @Override
    protected void execute(DukeCore core) {
        core.ui.showMessage("Argument: " + cmd.getArg() + System.lineSeparator() + "Switch: "
                + cmd.getSwitchVal("switch"));
    }
}
