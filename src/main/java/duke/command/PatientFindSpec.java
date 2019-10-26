package duke.command;

public class PatientFindSpec extends ArgSpec {

    private static final PatientFindSpec spec = new PatientFindSpec();

    public static PatientFindSpec getSpec() {
        return spec;
    }

    private PatientFindSpec() {
        emptyArgMsg = "You didn't tell me anything about the search!";
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(
                new Switch("impression", String.class, true, ArgLevel.NONE, "i"),
                new Switch("evidence", String.class, true, ArgLevel.NONE, "e"),
                new Switch("treatment", String.class, true, ArgLevel.NONE, "t")
        );
    }
}
