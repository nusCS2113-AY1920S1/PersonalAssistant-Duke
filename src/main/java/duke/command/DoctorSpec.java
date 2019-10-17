package duke.command;

public class DoctorSpec extends ArgSpec {

    private static final DoctorSpec spec = new DoctorSpec();

    public static DoctorSpec getSpec() {
        return spec;
    }

    private DoctorSpec() {
        emptyArgMsg = "You didn't tell me what to do!";
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(
               new Switch("switch", String.class, true, ArgLevel.OPTIONAL, "s")
        );
    }
}
