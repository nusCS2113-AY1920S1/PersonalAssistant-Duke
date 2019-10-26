package duke.command;

public class UpSpec extends ArgSpec {
    private static final UpSpec spec = new UpSpec();

    public static UpSpec getSpec() {
        return spec;
    }

    private UpSpec() {
        cmdArgLevel = ArgLevel.NONE;
        initSwitches();
    }
}
