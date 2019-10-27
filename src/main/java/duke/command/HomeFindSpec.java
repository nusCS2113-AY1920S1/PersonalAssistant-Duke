package duke.command;

public class HomeFindSpec extends ArgSpec {

    private static final HomeFindSpec spec = new HomeFindSpec();

    public static HomeFindSpec getSpec() {
        return spec;
    }

    private HomeFindSpec() {
        emptyArgMsg = "You didn't tell me anything about the search!";
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(
                new Switch("patient", String.class, true, ArgLevel.NONE, "p"),
                new Switch("impression", String.class, true, ArgLevel.NONE, "i"),
                new Switch("evidence", String.class, true, ArgLevel.NONE, "e"),
                new Switch("treatment", String.class, true, ArgLevel.NONE, "t")
        );
    }
}
