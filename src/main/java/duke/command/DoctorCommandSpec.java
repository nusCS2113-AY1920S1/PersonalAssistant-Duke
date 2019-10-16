package duke.command;

public class DoctorCommandSpec extends ArgCommandSpec {

    private static final DoctorCommandSpec spec = new DoctorCommandSpec();

    public static DoctorCommandSpec getSpec() {
        return spec;
    }

    private DoctorCommandSpec() {
        emptyArgMsg = "You didn't tell me what to do!";
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(
               new Switch("switch", String.class, true, ArgLevel.OPTIONAL, "s")
        );
    }
}
