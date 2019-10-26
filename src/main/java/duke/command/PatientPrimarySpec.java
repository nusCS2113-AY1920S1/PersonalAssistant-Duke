package duke.command;

public class PatientPrimarySpec extends ArgSpec {
    private static final PatientPrimarySpec spec = new PatientPrimarySpec();

    public static PatientPrimarySpec getSpec() {
        return spec;
    }

    private PatientPrimarySpec() {
        emptyArgMsg = "You didn't tell me which impression to set!";
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches();
    }
}
