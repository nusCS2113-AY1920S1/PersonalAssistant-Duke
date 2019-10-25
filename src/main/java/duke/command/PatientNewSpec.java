package duke.command;

public class PatientNewSpec extends ArgSpec {

    private static final PatientNewSpec spec = new PatientNewSpec();

    public static PatientNewSpec getSpec() {
        return spec;
    }

    private PatientNewSpec() {
        emptyArgMsg = "You didn't tell me anything about the patient!";
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(
                new Switch("name", String.class, false, ArgLevel.REQUIRED, "n"),
                new Switch("description", String.class, false, ArgLevel.REQUIRED, "d")
        );
    }
}
