package duke.command;

public class HomeHistorySpec extends ArgSpec {
    private static final HomeHistorySpec spec = new HomeHistorySpec();

    public static HomeHistorySpec getSpec() {
        return spec;
    }

    private HomeHistorySpec() {
        emptyArgMsg = "You didn't tell me anything about the patient!";
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(
                new Switch("message", String.class, false, ArgLevel.REQUIRED, "m"),
                new Switch("rewrite", String.class, true, ArgLevel.REQUIRED, "r")
        );
    }
}
