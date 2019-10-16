package duke.command;

public class DoctorCommandSpec extends ArgCommandSpec {

    private static final DoctorCommandSpec spec = new DoctorCommandSpec();

    public static DoctorCommandSpec getSpec() {
        return spec;
    }

    private DoctorCommandSpec() {

    }
}
