package duke.command;

public class PatientHistorySpec extends ArgSpec {

    private static final PatientHistorySpec spec = new PatientHistorySpec();

    public static PatientHistorySpec getSpec() {
        return spec;
    }

    private PatientHistorySpec() {
        emptyArgMsg = "You didn't tell me anything on what to add!";
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(
                new Switch("note", String.class, false, ArgLevel.REQUIRED, "n")
        );
    }
}
